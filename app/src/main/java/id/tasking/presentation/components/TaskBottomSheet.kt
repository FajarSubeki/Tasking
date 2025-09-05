package id.tasking.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskBottomSheet(
    title: String = "",
    description: String = "",
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
) {
    var inputTitle by remember { mutableStateOf(title) }
    var inputDesc by remember { mutableStateOf(description) }

    var titleTouched by remember { mutableStateOf(false) }

    val maxTitleLength = 50
    val maxDescLength = 100

    val isTitleValid = inputTitle.isNotBlank() && inputTitle.length <= maxTitleLength
    val isDescValid = inputDesc.length <= maxDescLength
    val canSave = isTitleValid && isDescValid

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = if (title.isBlank()) "Add Task" else "Edit Task",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = inputTitle,
                onValueChange = {
                    if (it.length <= maxTitleLength) {
                        inputTitle = it
                        titleTouched = true
                    }
                },
                label = { Text("Task Title *") },
                modifier = Modifier.fillMaxWidth(),
                isError = titleTouched && inputTitle.isBlank()
            )
            if (titleTouched && inputTitle.isBlank()) {
                Text(
                    text = "Title Required",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (inputTitle.length >= maxTitleLength) {
                Text(
                    text = "Maximum title $maxTitleLength character",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = inputDesc,
                onValueChange = {
                    if (it.length <= maxDescLength) inputDesc = it
                },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            if (inputDesc.length >= maxDescLength) {
                Text(
                    text = "Maximum description $maxDescLength character",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSave(inputTitle, inputDesc) },
                    enabled = canSave
                ) { Text("Save") }
            }
        }
    }
}


