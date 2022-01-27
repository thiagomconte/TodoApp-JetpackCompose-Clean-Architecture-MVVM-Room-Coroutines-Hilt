package com.example.todoapp.todo_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.LightGray
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.theme.gradientBackground

@Composable
fun EmptyList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(45f)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Icon(
                painter = painterResource(id = R.drawable.tasklist),
                contentDescription = "Empty list",
                modifier = Modifier.align(CenterHorizontally),
                tint = LightGray
            )
            Text(
                text = "You haven't added tasks yet",
                color = LightGray,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewEmptyList() {
    TodoAppTheme {
        EmptyList()
    }
}
