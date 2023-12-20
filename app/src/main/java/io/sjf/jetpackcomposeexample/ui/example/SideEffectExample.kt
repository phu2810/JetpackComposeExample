package io.sjf.jetpackcomposeexample.ui.example

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// LaunchedEffect is used for coroutine-based side-effects.
// It automatically cancels when the composable leaves the composition.
@Composable
fun LaunchedEffectComposable() {

    var status by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        status = "Running"
        // call a suspend function here
        delay(5000)
        status = "Finished"
    }
    Text(text = "LaunchedEffect: $status")
}

// Provides a CoroutineScope tied to the composable's lifecycle.
// Useful when you need to launch coroutines in response to events like button clicks.
@Composable
fun CoroutineScopeExample() {
    val scope = rememberCoroutineScope()
    var status by rememberSaveable { mutableStateOf("") }

    Button(onClick = {
        scope.launch {
            status = "Running"
            // call a suspend function here
            delay(5000)
            status = "Finished"
        }
    }) {
        Text("Perform Action")
    }
    Text(text = "CoroutineScope: $status")
}

// For managing cleanup operations and resources that need to be released.
@Composable
fun DisposableEffectExample(onEnter: () -> Unit, onDispose: () -> Unit) {
    val currentOnEnter by rememberUpdatedState(onEnter)
    val currentOnDispose by rememberUpdatedState(onDispose)
    DisposableEffect(Unit) {
        currentOnEnter()
        onDispose {
            // cleanup logic here
            currentOnDispose()
        }
    }
}

@Composable
fun SideEffectExample() {
    var showDisposeEffectExample by remember { mutableStateOf(true) }
    var disposableEffectStatus by remember { mutableStateOf("") }

    Column {
        LaunchedEffectComposable()
        CoroutineScopeExample()
        if (showDisposeEffectExample) {
            DisposableEffectExample(
                onEnter = { disposableEffectStatus = "Entered" },
                onDispose = { disposableEffectStatus = "Disposed" }
            )
        }
        Button(onClick = { showDisposeEffectExample = !showDisposeEffectExample }) {
            Text("Toggle Dispose Effect (Current: $disposableEffectStatus)")
        }
    }
}