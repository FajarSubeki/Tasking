package id.tasking.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.tasking.data.local.TaskDatabase
import id.tasking.data.repository.TaskRepositoryImpl
import id.tasking.domain.repository.TaskRepository
import id.tasking.domain.usecase.AddTaskUseCase
import id.tasking.domain.usecase.DeleteTaskUseCase
import id.tasking.domain.usecase.GetTasksUseCase
import id.tasking.domain.usecase.TaskUseCases
import id.tasking.domain.usecase.UpdateTaskUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TaskDatabase =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_db").build()

    @Provides
    @Singleton
    fun provideRepository(db: TaskDatabase): TaskRepository =
        TaskRepositoryImpl(db.dao)

    @Provides
    @Singleton
    fun provideUseCases(repo: TaskRepository): TaskUseCases =
        TaskUseCases(
            addTask = AddTaskUseCase(repo),
            getTasks = GetTasksUseCase(repo),
            updateTask = UpdateTaskUseCase(repo),
            deleteTask = DeleteTaskUseCase(repo)
        )
}
