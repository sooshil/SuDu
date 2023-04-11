package com.sukajee.sudu.data.model

data class MainUiState(
    val sudus: List<Sudu> = emptyList(),
    val loading: Boolean = false
)
