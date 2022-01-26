package com.example.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val limitDate: String
)
