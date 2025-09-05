package id.tasking.presentation.task.viewmodel

import id.tasking.domain.model.Task

sealed class TaskState {
    object Loading : TaskState()
    data class Success(val tasks: List<Task>) : TaskState()
    object Empty : TaskState()
    data class Error(val message: String) : TaskState()

    data class InputState(
        val newTaskTitle: String = "",
        val newTaskDescription: String = ""
    )
}
