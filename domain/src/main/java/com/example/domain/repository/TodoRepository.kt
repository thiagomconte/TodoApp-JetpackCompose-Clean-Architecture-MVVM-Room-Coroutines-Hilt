package com.example.domain.repository

import com.example.domain.entity.Todo
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo): Flow<Resource<String>>

    suspend fun getTodos(): Flow<Resource<List<Todo>>>

    suspend fun getTodoById(id: Int): Flow<Resource<Todo?>>

    suspend fun deleteTodo(todo: Todo): Flow<Resource<String>>

    suspend fun updateTodo(todo: Todo): Flow<Resource<String>>
}
