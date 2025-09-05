package id.tasking.presentation.task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import id.tasking.domain.model.Task

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
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.title,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                style = MaterialTheme.typography.bodyLarge
            )
            if (!task.description.isNullOrBlank()) {
                Text(text = task.description ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Row {
            if (!task.isCompleted) {
                TextButton(onClick = { onEdit(task) }) { Text("Edit") }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = { onComplete(task) }) { Text("Complete") }
            }
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = { onDelete(task) }, colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)) {
                Text("Delete")
            }
        }
    }
}
