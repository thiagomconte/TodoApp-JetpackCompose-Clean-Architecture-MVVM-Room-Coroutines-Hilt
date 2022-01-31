package com.example.todoapp.add_edit_todo.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.Todo
import com.example.domain.utils.TodoState
import com.example.todoapp.add_edit_todo.AddEditTodoEvent
import com.example.todoapp.add_edit_todo.AddEditTodoViewModel
import com.example.todoapp.ui.theme.Black500
import com.example.todoapp.ui.theme.LightGray
import com.example.todoapp.ui.theme.Yellow400
import com.example.todoapp.ui.theme.gradientBackground
import com.example.todoapp.utils.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val title = remember { mutableStateOf(TextFieldValue("")) }
    val description = remember { mutableStateOf(TextFieldValue("")) }
    val isChecked = remember { mutableStateOf(false) }

    val todo = viewModel.state.collectAsState(initial = TodoState.Empty).value
    if (todo is TodoState.Success) {
        title.value = TextFieldValue(todo.data.title)
        description.value = TextFieldValue(todo.data.description)
        isChecked.value = todo.data.isDone
    }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(45f)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            "Add Tasks",
            style = MaterialTheme.typography.h2,
            color = Yellow400,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        InputText(onTextChange = {
            title.value = it
        }, field = title, height = 64.dp, text = "Title")
        InputText(onTextChange = {
            description.value = it
        }, field = description, height = 300.dp, text = "Description")
        Row() {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = !isChecked.value },
                colors = CheckboxDefaults.colors(
                    checkedColor = Yellow400,
                    checkmarkColor = Black500,
                    disabledColor = LightGray,
                ),
                modifier = Modifier.align(CenterVertically)
            )
            Text(
                "Task has been completed",
                modifier = Modifier.align(CenterVertically),
                color = Color.LightGray
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(
                onClick = {
                    when (todo) {
                        is TodoState.Empty -> viewModel.onEvent(
                            AddEditTodoEvent.AddTodo(
                                Todo(
                                    title = title.value.text,
                                    description = description.value.text,
                                    isDone = isChecked.value,
                                    limitDate = ""
                                )
                            )
                        )
                        is TodoState.Success -> viewModel.onEvent(
                            AddEditTodoEvent.EditTodo(
                                Todo(
                                    id = todo.data.id,
                                    title = title.value.text,
                                    description = description.value.text,
                                    isDone = isChecked.value,
                                    limitDate = ""
                                )
                            )
                        )
                        else -> Unit
                    }
                },
                content = {
                    Text(text = if (todo is TodoState.Empty) "Add" else "Update")
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow400,
                    contentColor = Black500
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 12.dp,
                    pressedElevation = 16.dp,
                    disabledElevation = 0.dp
                ),
                modifier = Modifier.width(100.dp)
            )
            if (todo is TodoState.Success) {
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditTodoEvent.DeleteTodo(todo.data))
                    },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 12.dp,
                        pressedElevation = 16.dp,
                        disabledElevation = 0.dp
                    ),
                    content = {
                        Text(text = "REMOVE")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error,
                        contentColor = Black500
                    ),
                    modifier = Modifier.width(100.dp)
                )
            }
        }
    }
}

@Composable
fun InputText(
    onTextChange: (TextFieldValue) -> Unit,
    field: MutableState<TextFieldValue>,
    height: Dp,
    text: String
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(20.dp),
        value = field.value,
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text, color = Yellow400)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            unfocusedLabelColor = Yellow400,
            focusedLabelColor = Yellow400,
            cursorColor = Yellow400,
            focusedIndicatorColor = Yellow400,
            unfocusedIndicatorColor = Yellow400,
            textColor = Color.White,
        )
    )
}
