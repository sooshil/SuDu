package com.sukajee.sudu.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudus")
data class Sudu(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val description: String?,
    val isCompleted: Boolean? = false,
    val deadline: Long? = -1L,
    val created: Long?,
    val lastModified: Long?
)
