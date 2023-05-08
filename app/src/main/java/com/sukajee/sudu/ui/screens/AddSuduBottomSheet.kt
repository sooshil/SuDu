package com.sukajee.sudu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.sudu.data.model.BottomSheetUiState
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.ui.compsables.SuduTextField

@Composable
fun AddSuduBottomSheet(
    uiState: BottomSheetUiState,
    modifier: Modifier = Modifier,
    onSubmitClick: (Sudu) -> Unit,
    onUpdateClicked: (Sudu) -> Unit,
    onCancelled: () -> Unit
) {
    var suduId by remember { mutableStateOf(0) }
    var titleState by remember { mutableStateOf("") }
    val colorState by remember { mutableStateOf(0) }
    var descriptionState by remember { mutableStateOf("") }
    var completedState by remember { mutableStateOf(false) }
    var deadlineState by remember { mutableStateOf("") }


    LaunchedEffect(uiState.currentSudu) {
        suduId = uiState.currentSudu?.id ?: 0
        titleState = uiState.currentSudu?.title ?: ""
        descriptionState = uiState.currentSudu?.description ?: ""
        completedState = uiState.currentSudu?.isCompleted == true
        deadlineState = uiState.currentSudu?.let { it.deadline.toString() } ?: ""
    }

    Box(modifier = modifier.padding(16.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SuduTextField(
                value = titleState,
                onValueChange = {
                    titleState = it
                },
                label = "ToDo"
            )
            Spacer(modifier = Modifier.height(8.dp))
            SuduTextField(
                value = descriptionState,
                onValueChange = {
                    descriptionState = it
                },
                maxLines = 5,
                label = "Description"
            )
            Spacer(modifier = Modifier.height(8.dp))
            uiState.currentSudu?.let {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Completed?",
                        fontSize = 16.sp
                    )
                    Checkbox(
                        checked = completedState,
                        onCheckedChange = {
                            completedState = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            SuduTextField(
                value = deadlineState,
                onValueChange = {
                    deadlineState = it
                },
                label = "Deadline",
                endIcon = Icons.Default.Menu
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = modifier.fillMaxWidth()) {
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    onClick = {
                        titleState = ""
                        descriptionState = ""
                        completedState = false
                        deadlineState = ""
                        uiState.currentSudu?.let { onCancelled() }
                    }
                ) {
                    Text(text = uiState.currentSudu?.let { "Cancel" } ?: "Clear")
                }
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = {
                        val sudu = Sudu(
                            id = suduId,
                            title = titleState,
                            color = colorState,
                            description = descriptionState,
                            isCompleted = completedState,
                            deadline = deadlineState.toLongOrNull() ?: -1L,
                            created = System.currentTimeMillis(),
                            lastModified = System.currentTimeMillis()
                        )
                        uiState.currentSudu?.let {
                            onUpdateClicked(sudu)
                        } ?: onSubmitClick(sudu)
                    }
                ) {
                    Text(text = uiState.currentSudu?.let { "Update" } ?: "Submit")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
