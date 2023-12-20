package io.sjf.jetpackcomposeexample.ui.example
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicStateHandling(onExampleEvent: () -> Unit) {
    // Basic remember
    var counter by remember { mutableStateOf(0) }

    // RememberSaveable for handling configuration changes
    var text by rememberSaveable { mutableStateOf("Hello") }

    // RememberUpdatedState to capture the latest value of exampleEvent
    val exampleEvent by rememberUpdatedState(onExampleEvent)

    // LaunchedEffect: runs a coroutine when the composition is first created
    LaunchedEffect(true) {
        delay(1000)
        onExampleEvent()
    }

    // UI
    Column {
        Button(onClick = { counter++ }) {
            Text("Counter: $counter")
        }
        TextField(value = text, onValueChange = { text = it })
        Text("Updated Text: $text")
    }
}
