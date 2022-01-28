package com.example.todoapp.todo_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entity.Todo
import com.example.todoapp.todo_list.TodoListEvent
import com.example.todoapp.ui.theme.LightGray
import com.example.todoapp.ui.theme.Yellow400

@Composable
fun TodoItem(
    todo: Todo,
    onTodoClick: (TodoListEvent.OnTodoClick) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {
                onTodoClick(TodoListEvent.OnTodoClick(todo.id!!))
            }
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column() {
                Text(
                    todo.title, color = Color.White, style = MaterialTheme.typography.body1,
                )
                Text("in 6 hours", color = LightGray, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Yellow400,
                modifier = Modifier
                    .align(CenterVertically)
                    .size(28.dp)
            )
        }
    }
}
