package com.sukajee.sudu.data.util

import android.view.KeyEvent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.handleKeyboardTab(focusManager: FocusManager): Modifier {
    return this.onPreviewKeyEvent {
        /* To shift down the focus when Tab is pressed from external keyboard */
        if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
            focusManager.moveFocus(FocusDirection.Down)
            true
        } else {
            false
        }
    }
}
