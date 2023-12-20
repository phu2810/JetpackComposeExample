package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Hoist state example
@Composable
fun Counter(count: Int, onIncrement: () -> Unit) {
    Column {
        Text("Count: $count")
        Button(onClick = onIncrement) {
            Text("Increment")
        }
    }
}

@Composable
fun DisplayCounter(count: Int) {
    Text("Count: $count")
}

@Composable
fun HoistStateExample() {
    var count by remember { mutableIntStateOf(0) }

    Column {
        Counter(count = count, onIncrement = { count++ })
        Spacer(modifier = Modifier.height(16.dp))
        DisplayCounter(count = count)
    }
}
