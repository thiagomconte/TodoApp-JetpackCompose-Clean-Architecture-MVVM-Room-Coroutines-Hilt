package com.example.domain.use_case.get_todos

import com.example.domain.entity.Todo
import com.example.domain.repository.TodoRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.TodoState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val repo: TodoRepository
) {

    @ExperimentalCoroutinesApi
    operator fun invoke(): Flow<TodoState<List<Todo>>> {
        return channelFlow {
            repo.getTodos().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data.isEmpty()) send(TodoState.Empty)
                        else send(TodoState.Success(resource.data))
                    }
                    is Resource.Error -> send(TodoState.Error(resource.error))
                }
            }
        }
    }
}
