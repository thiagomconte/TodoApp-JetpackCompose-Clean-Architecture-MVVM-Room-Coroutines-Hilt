package com.example.data.database

import androidx.room.*
import com.example.domain.entity.Todo

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * FROM Todo")
    suspend fun getTodos(): List<Todo>

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodo(todo: Todo)
}
