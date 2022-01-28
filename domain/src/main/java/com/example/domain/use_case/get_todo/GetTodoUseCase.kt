package com.example.domain.use_case.get_todo

import com.example.domain.entity.Todo
import com.example.domain.repository.TodoRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.TodoState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val repo: TodoRepository
) {

    @ExperimentalCoroutinesApi
    operator fun invoke(id: Int): Flow<TodoState<Todo>> {
        return channelFlow {
            repo.getTodoById(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data != null) send(TodoState.Success(resource.data))
                        else send(TodoState.Empty)
                    }
                    is Resource.Error -> send(TodoState.Error(resource.error))
                }
            }
        }
    }
}
