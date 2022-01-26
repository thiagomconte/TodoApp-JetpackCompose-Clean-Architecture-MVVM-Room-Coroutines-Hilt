package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.data.database.TodoDao
import com.example.data.database.TodoDataBase
import com.example.data.repository.TodoRepositoryImpl
import com.example.data.utils.Constants
import com.example.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDataBase {
        return Room.databaseBuilder(app, TodoDataBase::class.java, Constants.DATA_BASE).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(db: TodoDataBase): TodoDao {
        return db.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(dao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(dao)
    }
}
