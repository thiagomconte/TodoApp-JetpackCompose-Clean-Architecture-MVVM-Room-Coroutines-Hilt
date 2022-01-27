package com.example.todoapp.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Todo
import com.example.domain.use_case.get_todos.GetTodosUseCase
import com.example.domain.utils.TodoState
import com.example.todoapp.utils.Constants
import com.example.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val useCase: GetTodosUseCase
) : ViewModel() {

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _state: MutableStateFlow<TodoState<List<Todo>>> =
        MutableStateFlow(TodoState.Loading)
    val state: StateFlow<TodoState<List<Todo>>> get() = _state

    @ExperimentalCoroutinesApi
    fun getTodos() {
        viewModelScope.launch {
            useCase().onStart {
                _state.value = TodoState.Loading
            }.collectLatest {
                _state.value = it
            }
        }
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnNewTodo -> {
                sendEvent(UiEvent.Navigate(Constants.Routes.ADD_EDIT))
            }
            is TodoListEvent.OnTodoClick -> {
                sendEvent(UiEvent.Navigate("${Constants.Routes.ADD_EDIT}?id=${event.id}"))
            }
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}
