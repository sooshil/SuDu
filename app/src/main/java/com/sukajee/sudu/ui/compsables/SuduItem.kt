package com.sukajee.sudu.ui.compsables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.sudu.data.model.Sudu

@Composable
fun SuduItem(
    sudu: Sudu,
    modifier: Modifier = Modifier,
    onCheckedChange: (Sudu) -> Unit,
    onSuduClick: (Sudu) -> Unit
) {
    var checkedState by remember {
        mutableStateOf(sudu.isCompleted)
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(sudu.color)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onSuduClick(sudu)
                    }
            ) {
                sudu.title.let {
                    Text(
                        text = sudu.title,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        fontSize = 16.sp
                    )
                    Text(
                        text = sudu.description ?: "",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                        fontSize = 12.sp
                    )
                }
            }
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = !checkedState
                    onCheckedChange(
                        sudu.copy(
                            isCompleted = checkedState
                        )
                    )
                }
            )
        }
    }

}
