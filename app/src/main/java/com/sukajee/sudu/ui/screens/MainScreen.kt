package com.sukajee.sudu.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.sukajee.sudu.data.model.BottomSheetUiState
import com.sukajee.sudu.data.model.MainUiState
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.ui.SuduViewModel
import com.sukajee.sudu.ui.compsables.SuduItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: SuduViewModel
) {
    val uiState by viewModel.mainUiState.collectAsState()
    val bottomSheetUiState by viewModel.bottomSheetUiState.collectAsState()

    StatelessMainScreen(
        mainUiState = uiState,
        bottomSheetUiState = bottomSheetUiState,
        onSubmitClicked = { sudu, isUpdate ->
            if (isUpdate) viewModel.updateSudu(sudu) else viewModel.insertSudu(sudu)
        },
        onCompletedUpdate = { updatedSudu ->
            viewModel.updateSudu(updatedSudu)
        },
        onSuduClick = { clickedSuduId ->
            viewModel.viewModelScope.launch {
                viewModel.onSuduClick(clickedSuduId)
            }
        },
        onCancelClicked = {
            viewModel.updateBottomSheetUiState(
                currentSudu = null,
                isEditMode = false
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatelessMainScreen(
    mainUiState: MainUiState,
    bottomSheetUiState: BottomSheetUiState,
    onSuduClick: (suduId: Int) -> Unit,
    onSubmitClicked: (sudu: Sudu, isUpdate: Boolean) -> Unit,
    onCompletedUpdate: (Sudu) -> Unit,
    onCancelClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomsheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    ModalBottomSheetLayout(
        sheetState = bottomsheetState,
        sheetContent = {
            AddSuduBottomSheet(
                uiState = bottomSheetUiState,
                onSubmitClick = {
                    onSubmitClicked(it, false)
                    scope.launch {
                        bottomsheetState.hide()
                    }
                },
                onCancelled = {
                    onCancelClicked()
                    scope.launch {
                        bottomsheetState.hide()
                    }
                },
                onUpdateClicked = {
                    onSubmitClicked(it, true)
                    scope.launch {
                        bottomsheetState.hide()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Scaffold(
            topBar = { SuduAppBar() },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onCancelClicked()
                        scope.launch {
                            bottomsheetState.show()
                        }
                    },
                    backgroundColor = Color(0xFF5B01B4),
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        modifier = Modifier.size(28.dp),
                        contentDescription = "Add new todo."
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (mainUiState.loading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) { CircularProgressIndicator() }
                } else {
                    mainUiState.sudus.let { sudus ->
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(sudus.size) { index ->
                                SuduItem(
                                    sudu = sudus[index],
                                    onCheckedChange = { onCompletedUpdate(it) },
                                    onSuduClick = {
                                        onSuduClick(it.id)
                                        scope.launch {
                                            bottomsheetState.show()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuduAppBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color(0xFF5B01B4)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f),
            text = "My ToDos",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}
