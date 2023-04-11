package com.sukajee.sudu.data.model

data class BottomSheetUiState(
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val deadline: Long = -1L,
    val isLoading: Boolean = false,
    val isEditState: Boolean = false
)
