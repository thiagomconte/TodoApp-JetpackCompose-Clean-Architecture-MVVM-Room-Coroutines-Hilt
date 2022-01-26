package com.example.data.repository

import com.example.data.database.TodoDao
import com.example.domain.entity.Todo
import com.example.domain.repository.TodoRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo): Flow<Resource<String>> {
        return flow {
            try {
                dao.insertTodo(todo)
                emit(Resource.Success("Todo successfully saved"))
            } catch (e: Exception) {
                emit(Resource.Error("Could not save task"))
            }
        }
    }

    override suspend fun getTodos(): Flow<Resource<List<Todo>>> {
        return flow {
            try {
                val todos = dao.getTodos()
                emit(Resource.Success(todos))
            } catch (e: Exception) {
                emit(Resource.Error("Could not load your tasks"))
            }
        }
    }

    override suspend fun getTodoById(id: Int): Flow<Resource<Todo?>> {
        return flow {
            try {
                val todo = dao.getTodoById(id)
                emit(Resource.Success(todo))
            } catch (e: Exception) {
                emit(Resource.Error("Could not load task"))
            }
        }
    }

    override suspend fun deleteTodo(todo: Todo): Flow<Resource<String>> {
        return flow {
            try {
                dao.deleteTodo(todo)
                emit(Resource.Success("Todo successfully deleted"))
            } catch (e: Exception) {
                emit(Resource.Error("Could not save task"))
            }
        }
    }

    override suspend fun updateTodo(todo: Todo): Flow<Resource<String>> {
        return flow {
            try {
                dao.updateTodo(todo)
                emit(Resource.Success("Todo successfully updated"))
            } catch (e: Exception) {
                emit(Resource.Error("Could not save task"))
            }
        }
    }
}
