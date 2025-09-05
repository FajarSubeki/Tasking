package id.tasking.presentation.task.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    taskToEdit = null
                    showBottomSheet = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Header: Title + Subtitle
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tasking",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Add, edit, complete or delete your tasks easily",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
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
    }

    if (showBottomSheet) {
        TaskBottomSheet(
            title = taskToEdit?.title ?: "",
            description = taskToEdit?.description ?: "",
            onDismiss = { showBottomSheet = false },
            onSave = { title, desc ->
                taskToEdit?.let { task ->
                    viewModel.editTask(task, title, desc)
                } ?: viewModel.addTask(title, desc)
                showBottomSheet = false
            }
        )
    }
}
