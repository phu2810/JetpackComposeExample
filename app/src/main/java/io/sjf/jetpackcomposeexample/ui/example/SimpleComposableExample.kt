package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.sjf.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

@Composable
fun SimpleComposableExample() {
    var text by remember { mutableStateOf("Hello, Jetpack Compose!") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { text = "Button clicked!" }) {
            Text("Click Me")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeExampleTheme {
        SimpleComposableExample()
    }
}