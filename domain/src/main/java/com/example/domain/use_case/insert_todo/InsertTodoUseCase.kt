package com.example.domain.use_case.insert_todo

import com.example.domain.entity.Todo
import com.example.domain.repository.TodoRepository
import com.example.domain.utils.Resource
import com.example.domain.utils.TodoState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(
    private val repo: TodoRepository
) {

    operator fun invoke(todo: Todo): Flow<TodoState<String>> {
        return flow {
            repo.insertTodo(todo).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> emit(TodoState.Success(resource.data))
                    is Resource.Error -> emit(TodoState.Error(resource.error))
                }
            }
        }
    }
}
