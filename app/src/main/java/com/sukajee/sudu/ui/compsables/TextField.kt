package com.sukajee.sudu.ui.compsables

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SuduTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 100,
    endIcon: ImageVector? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            onValueChange(text)
        },
        trailingIcon = {
            if (endIcon != null) IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = endIcon, contentDescription = "open calendar")
            }
        },
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None,
        maxLines = maxLines,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Green.copy(alpha = 0.05f),
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Green.copy(0.9f),
            unfocusedIndicatorColor = Color.Green.copy(0.3f),
            focusedLabelColor = Color(0xFF0A8149)
        )
    )
}
