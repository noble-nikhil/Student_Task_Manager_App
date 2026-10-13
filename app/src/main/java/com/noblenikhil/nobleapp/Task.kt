package com.noblenikhil.nobleapp

import java.util.UUID

enum class Priority {
    HIGH, MEDIUM, LOW
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var notes: String = "",
    var subject: String = "",
    var dueDate: String = "",
    var priority: Priority = Priority.MEDIUM,
    var isCompleted: Boolean = false
)
