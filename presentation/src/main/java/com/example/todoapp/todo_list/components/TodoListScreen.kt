package com.example.todoapp.todo_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.utils.TodoState
import com.example.todoapp.todo_list.TodoListEvent
import com.example.todoapp.todo_list.TodoListViewModel
import com.example.todoapp.ui.theme.Black100
import com.example.todoapp.ui.theme.Yellow400
import com.example.todoapp.ui.theme.gradientBackground
import com.example.todoapp.utils.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.collectAsState(initial = TodoState.Loading).value

    LaunchedEffect(Unit) {
        viewModel.getTodos()
        viewModel.channel.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TodoListEvent.OnNewTodo)
                },
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
                if (state is TodoState.Success) {
                    LazyColumn() {
                        items(items = state.data) { data ->
                            TodoItem(data) {
                                viewModel.onEvent(TodoListEvent.OnTodoClick(it.id))
                            }
                            Divider(color = Color.Black.copy(alpha = 0.6f))
                        }
                    }
                } else if (state is TodoState.Empty) {
                    EmptyList()
                }
            }
        }
    }
}
