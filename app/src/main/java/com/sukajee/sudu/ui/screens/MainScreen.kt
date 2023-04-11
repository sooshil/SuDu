package com.sukajee.sudu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.sudu.data.model.BottomSheetUiState
import com.sukajee.sudu.data.model.MainUiState
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.ui.SuduViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: SuduViewModel
) {
    val uiState by viewModel.mainUiState.collectAsState()
    StatelessMainScreen(
        mainUiState = uiState,
        onSubmitClicked = { viewModel.insertSudu(it) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatelessMainScreen(
    mainUiState: MainUiState,
    onSubmitClicked: (Sudu) -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomsheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    ModalBottomSheetLayout(
        sheetState = bottomsheetState,
        sheetContent = {
            AddSuduBottomSheet(
                onSubmitClick = {
                    onSubmitClicked(it)
                    scope.launch {
                        bottomsheetState.hide()
                    }
                },
                onCancelled = {
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
                mainUiState.sudus.let {
                    if (it.isNotEmpty()) Text(text = "${it.last().title}")
                    else Text(text = "List is empty now.")
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
