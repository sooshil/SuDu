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
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.ui.compsables.SuduTextField

@Composable
fun AddSuduBottomSheet(
    modifier: Modifier = Modifier,
    onSubmitClick: (Sudu) -> Unit,
    onCancelled: () -> Unit,
    isInEditMode: Boolean = false,
    editingSudu: Sudu? = null
) {
    var titleState by remember {
        mutableStateOf(if (isInEditMode) editingSudu?.title ?: "" else "")
    }
    var descriptionState by remember {
        mutableStateOf(if (isInEditMode) editingSudu?.description ?: "" else "")
    }
    var completedState by remember {
        mutableStateOf(if (isInEditMode) editingSudu?.isCompleted ?: false else false)
    }
    var deadlineState by remember {
        mutableStateOf(
            if (isInEditMode) editingSudu?.deadline
                ?: System.currentTimeMillis()
            else -1L
        )
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
                label = "Description"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (isInEditMode) {
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
            }
            if (isInEditMode) Spacer(modifier = Modifier.height(16.dp))
            SuduTextField(
                value = if (deadlineState != -1L) deadlineState.toString() else "",
                onValueChange = {
                    deadlineState = try {
                        it.toLong()
                    } catch (e: Exception) {
                        -1
                    }
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
                        deadlineState = -1L
                        if (isInEditMode) onCancelled()
                    }
                ) {
                    Text(text = if (isInEditMode) "Cancel" else "Clear")
                }
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = {
                        onSubmitClick(
                            Sudu(
                                title = titleState,
                                description = descriptionState,
                                isCompleted = completedState,
                                deadline = deadlineState,
                                created = System.currentTimeMillis(),
                                lastModified = System.currentTimeMillis()
                            )
                        )
                    }
                ) {
                    Text(text = if (isInEditMode) "Update" else "Submit")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
