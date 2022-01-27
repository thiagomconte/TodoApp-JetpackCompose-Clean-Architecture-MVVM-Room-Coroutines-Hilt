package com.example.todoapp.todo_list

sealed class TodoListEvent {
    object OnNewTodo : TodoListEvent()
    data class OnTodoClick(val id: Int) : TodoListEvent()
}
