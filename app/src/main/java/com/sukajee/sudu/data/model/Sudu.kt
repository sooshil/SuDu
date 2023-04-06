package com.sukajee.sudu.data.model

data class Sudu(
    val id: Int,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean? = false,
    val hasDeadline: Boolean? = false
)
