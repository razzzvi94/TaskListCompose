package com.example.tasklistcompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    private var _todoList = mutableStateOf(listOf<TodoItem>())
    val todoList: State<List<TodoItem>> = _todoList

    fun updateList(list: List<TodoItem>) {
        _todoList.value = list
    }

    fun getList(): List<TodoItem> {
        return _todoList.value
    }
}