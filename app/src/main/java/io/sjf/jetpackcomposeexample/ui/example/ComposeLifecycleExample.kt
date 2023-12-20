package io.sjf.jetpackcomposeexample.ui.example

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ComposeLifecycleExample() {
    var count by remember { mutableStateOf(0) }
    var lifecycleState by remember { mutableStateOf("Initialized") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Count: $count")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count++ }) {
            Text("Increment")
        }
        Text(text = "Lifecycle: $lifecycleState - $count")
    }

    // Initialization
    LaunchedEffect(Unit) {
        lifecycleState = "Initialized"
    }

    // Recomposition
    LaunchedEffect(count) {
        lifecycleState = "Recomposed"
    }

    // Leave Composition
    DisposableEffect(Unit) {
        onDispose {
            Log.d("ComposeLifecycle", "Leave Composition: Cleaning up.")
            lifecycleState = "Leave Composition"
        }
    }
}
