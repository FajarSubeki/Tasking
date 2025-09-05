package id.tasking.presentation.task.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.tasking.domain.model.Task
import id.tasking.domain.usecase.TaskUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCases: TaskUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state.asStateFlow()

    // Persist input field menggunakan SavedStateHandle
    private var inputTitle: String
        get() = savedStateHandle["title"] ?: ""
        set(value) { savedStateHandle["title"] = value }

    private var inputDescription: String
        get() = savedStateHandle["description"] ?: ""
        set(value) { savedStateHandle["description"] = value }

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                useCases.getTasks().collect { tasks ->
                    _state.value = when {
                        tasks.isEmpty() -> TaskState.Empty
                        else -> TaskState.Success(tasks)
                    }
                }
            } catch (e: Exception) {
                _state.value = TaskState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onTitleChange(title: String) {
        inputTitle = title
    }

    fun onDescriptionChange(desc: String) {
        inputDescription = desc
    }

    fun addTask(title: String, description: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            try {
                useCases.addTask(Task(title = title, description = description))
            } catch (e: Exception) {
                _state.value = TaskState.Error(e.message ?: "Failed to add task")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                useCases.deleteTask(task)
            } catch (e: Exception) {
                _state.value = TaskState.Error(e.message ?: "Failed to delete task")
            }
        }
    }

    fun editTask(task: Task, newTitle: String, newDesc: String?) {
        viewModelScope.launch {
            try {
                useCases.updateTask(task.copy(title = newTitle, description = newDesc))
            } catch (e: Exception) {
                _state.value = TaskState.Error(e.message ?: "Failed to edit task")
            }
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            try {
                useCases.updateTask(task.copy(isCompleted = true))
            } catch (e: Exception) {
                _state.value = TaskState.Error(e.message ?: "Failed to complete task")
            }
        }
    }

    // Optional: fungsi untuk Compose observe input
    fun getInputState(): TaskState.InputState =
        TaskState.InputState(
            newTaskTitle = inputTitle,
            newTaskDescription = inputDescription
        )
}
