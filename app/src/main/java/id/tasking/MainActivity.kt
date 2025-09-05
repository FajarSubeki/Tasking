package id.tasking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import id.tasking.presentation.task.ui.TaskScreen
import id.tasking.presentation.task.viewmodel.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Inject ViewModel via Hilt
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TaskScreen(viewModel = viewModel)
            }
        }
    }
}
