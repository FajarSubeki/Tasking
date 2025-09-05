package id.tasking.presentation.task.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.tasking.domain.model.Task
import id.tasking.presentation.components.TaskBottomSheet
import id.tasking.presentation.task.viewmodel.TaskState
import id.tasking.presentation.task.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    viewModel: TaskViewModel
) {
    val state by viewModel.state.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Button add task
        Button(onClick = {
            taskToEdit = null
            showBottomSheet = true
        }) { Text("Add Task") }

        Spacer(modifier = Modifier.height(16.dp))

        // Show UI based on state
        when (state) {
            is TaskState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is TaskState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No tasks available")
                }
            }
            is TaskState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Error: ${(state as TaskState.Error).message}")
                }
            }
            is TaskState.Success -> {
                val tasks = (state as TaskState.Success).tasks
                LazyColumn {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onEdit = {
                                taskToEdit = it
                                showBottomSheet = true
                            },
                            onDelete = { viewModel.deleteTask(it) },
                            onComplete = { viewModel.completeTask(it) }
                        )
                    }
                }
            }
        }
    }

    // BottomSheet untuk Add/Edit
    if (showBottomSheet) {
        TaskBottomSheet(
            title = taskToEdit?.title ?: "",
            description = taskToEdit?.description ?: "",
            onDismiss = { showBottomSheet = false },
            onSave = { title, desc ->
                if (taskToEdit != null) {
                    viewModel.editTask(taskToEdit!!, title, desc)
                } else {
                    viewModel.addTask(title, desc)
                }
                showBottomSheet = false
            }
        )
    }
}
