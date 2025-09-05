package id.tasking.data.repository

import id.tasking.data.local.TaskDao
import id.tasking.data.local.toDomain
import id.tasking.data.local.toEntity
import id.tasking.domain.model.Task
import id.tasking.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> =
        dao.getTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun addTask(task: Task) = dao.insertTask(task.toEntity())
    override suspend fun updateTask(task: Task) = dao.updateTask(task.toEntity())
    override suspend fun deleteTask(task: Task) = dao.deleteTask(task.toEntity())
}
