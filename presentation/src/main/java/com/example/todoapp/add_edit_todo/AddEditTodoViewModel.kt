package com.example.todoapp.add_edit_todo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Todo
import com.example.domain.use_case.delete_todo.DeleteTodoUseCase
import com.example.domain.use_case.get_todo.GetTodoUseCase
import com.example.domain.use_case.insert_todo.InsertTodoUseCase
import com.example.domain.use_case.update_todo.UpdateTodoUseCase
import com.example.domain.utils.TodoState
import com.example.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val insertTodoUseCase: InsertTodoUseCase,
    private val getTodoUseCase: GetTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _state: MutableStateFlow<TodoState<Todo>> = MutableStateFlow(TodoState.Empty)
    val state: StateFlow<TodoState<Todo>> get() = _state

    init {
        val todoId = savedStateHandle.get<Int>("id")
        if (todoId != -1) {
            viewModelScope.launch {
                getTodoUseCase(todoId!!).onStart {
                    _state.value = TodoState.Loading
                }.collectLatest {
                    _state.value = it
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.AddTodo -> {
                viewModelScope.launch {
                    insertTodoUseCase(event.todo).collectLatest { event ->
                        if (event is TodoState.Success) {
                            sendEvent(UiEvent.PopBackStack)
                        }
                    }
                }
            }
            is AddEditTodoEvent.EditTodo -> {
                viewModelScope.launch {
                    updateTodoUseCase(event.todo).collectLatest { event ->
                        if (event is TodoState.Success) {
                            sendEvent(UiEvent.PopBackStack)
                        }
                    }
                }
            }
            is AddEditTodoEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deleteTodoUseCase(event.todo).collectLatest {
                        sendEvent(UiEvent.PopBackStack)
                    }
                }
            }
            is AddEditTodoEvent.PopBackStack -> sendEvent(UiEvent.PopBackStack)
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}
