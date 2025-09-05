package id.tasking.presentation.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import id.tasking.domain.model.Task
import id.tasking.presentation.components.TaskActionMenu

@Composable
fun TaskItem(
    task: Task,
    onEdit: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onComplete: (Task) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFF2F2F2), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                style = MaterialTheme.typography.bodyLarge
            )
            if (!task.description.isNullOrBlank()) {
                Text(
                    text = task.description,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            TaskActionMenu(
                task = task,
                onEdit = onEdit,
                onComplete = onComplete,
                onDelete = onDelete
            )
        }

    }
}
