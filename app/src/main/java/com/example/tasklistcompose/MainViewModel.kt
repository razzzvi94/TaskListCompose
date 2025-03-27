package com.example.tasklistcompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _todoList = mutableStateOf(listOf<TodoItem>())
    val todoList: State<List<TodoItem>> = _todoList

    fun addTodo(todo: TodoItem) {
        _todoList.value += todo
    }

    fun removeTodo(index: Int) {
        _todoList.value = _todoList.value.toMutableList().apply { removeAt(index) }
    }
}