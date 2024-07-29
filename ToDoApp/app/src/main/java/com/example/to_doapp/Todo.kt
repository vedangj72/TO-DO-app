package com.example.to_doapp

data class Todo(
    val id: Int,
    val text: String,
    var isDone: Boolean=false
)
