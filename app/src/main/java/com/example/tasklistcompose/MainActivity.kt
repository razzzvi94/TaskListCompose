package com.example.tasklistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasklistcompose.ui.theme.TaskListComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskListComposeTheme {
                val viewModel: MainViewModel = viewModel()
                ToDoApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp(viewModel: MainViewModel) {
    var newTodoText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Todo List") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (newTodoText.isNotBlank()) {
                        val newTodo = TodoItem(id = System.currentTimeMillis(), text = newTodoText)
                        viewModel.addTodo(newTodo)
                        newTodoText = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add todo")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = newTodoText,
                onValueChange = { newTodoText = it },
                label = { Text("New Todo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.todoList.value.isEmpty()) {
                Text(
                    text = "No todos yet. Add a new todo!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.SpaceEvenly) {
                    itemsIndexed(viewModel.todoList.value) { index, listItem ->
                        TodoItemRow(
                            todoItem = listItem,
                            onDelete = {
                                viewModel.removeTodo(index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(todoItem: TodoItem, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(todoItem.text, modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Delete,
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskListComposeTheme {
        ToDoApp(viewModel = MainViewModel())
    }
}