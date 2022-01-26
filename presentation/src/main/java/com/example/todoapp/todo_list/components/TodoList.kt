package com.example.todoapp.todo_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.Black100
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.theme.Yellow400
import com.example.todoapp.ui.theme.gradientBackground

@Composable
fun TodoList() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                contentColor = Black100,
                backgroundColor = Yellow400
            ) {
                Icon(imageVector = Icons.Default.Add, "")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .gradientBackground(45f)
                .padding(vertical = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "All Tasks",
                    style = MaterialTheme.typography.h2,
                    color = Yellow400,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Divider(color = Color.Black.copy(alpha = 0.6f))
                LazyColumn() {
                    items(items = listOf("", "")) {
                        TodoItem()
                        Divider(color = Color.Black.copy(alpha = 0.6f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewTodoList() {
    TodoAppTheme {
        TodoList()
    }
}
