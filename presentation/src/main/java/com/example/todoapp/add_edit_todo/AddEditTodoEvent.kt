package com.example.todoapp.add_edit_todo

import com.example.domain.entity.Todo

sealed class AddEditTodoEvent {
    object PopBackStack : AddEditTodoEvent()
    data class AddTodo(val todo: Todo) : AddEditTodoEvent()
    data class EditTodo(val todo: Todo) : AddEditTodoEvent()
    data class DeleteTodo(val todo: Todo) : AddEditTodoEvent()
}
