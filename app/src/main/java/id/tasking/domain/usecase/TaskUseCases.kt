package id.tasking.domain.usecase

import id.tasking.domain.model.Task
import id.tasking.domain.repository.TaskRepository

class AddTaskUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(task: Task) = repo.addTask(task)
}

class GetTasksUseCase(private val repo: TaskRepository) {
    operator fun invoke() = repo.getTasks()
}

class UpdateTaskUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(task: Task) = repo.updateTask(task)
}

class DeleteTaskUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(task: Task) = repo.deleteTask(task)
}

data class TaskUseCases(
    val addTask: AddTaskUseCase,
    val getTasks: GetTasksUseCase,
    val updateTask: UpdateTaskUseCase,
    val deleteTask: DeleteTaskUseCase
)
