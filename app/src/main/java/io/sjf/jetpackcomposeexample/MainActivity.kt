package io.sjf.jetpackcomposeexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.sjf.jetpackcomposeexample.ui.example.BasicStateHandling
import io.sjf.jetpackcomposeexample.ui.example.ComposeArchitectureWithViewModelExample
import io.sjf.jetpackcomposeexample.ui.example.ComposeLifecycleExample
import io.sjf.jetpackcomposeexample.ui.example.ConversionToStateExample
import io.sjf.jetpackcomposeexample.ui.example.HoistStateExample
import io.sjf.jetpackcomposeexample.ui.example.LayoutExample
import io.sjf.jetpackcomposeexample.ui.example.SideEffectExample
import io.sjf.jetpackcomposeexample.ui.example.SimpleComposableExample

// Define a simple sealed class to represent different composables
sealed class ExampleItem(val name: String) {
    object SimpleComposableExample : ExampleItem("Simple Composable")
    object ComposeLifecycleExample : ExampleItem("Compose Lifecycle")
    object BasicStateHandlingExample : ExampleItem("Basic State Handling")
    object ConversionToStateExample : ExampleItem("Conversion to State")
    object HoistStateExample : ExampleItem("Hoist State")
    object SideEffectExample : ExampleItem("Side Effect")
    object LayoutExample : ExampleItem("Layout")
    object ComposeArchitectureExample : ExampleItem("Compose Architecture")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleDetailScreen(
    itemName: String,
    onBackPressed: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(itemName) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (itemName) {
                ExampleItem.SimpleComposableExample.name -> SimpleComposableExample()
                ExampleItem.ComposeLifecycleExample.name -> ComposeLifecycleExample()
                ExampleItem.BasicStateHandlingExample.name -> BasicStateHandling {
                    Log.d("MainActivity", "Example event")
                }
                ExampleItem.ConversionToStateExample.name -> ConversionToStateExample()
                ExampleItem.HoistStateExample.name -> HoistStateExample()
                ExampleItem.SideEffectExample.name -> SideEffectExample()
                ExampleItem.LayoutExample.name -> LayoutExample()
                ExampleItem.ComposeArchitectureExample.name -> ComposeArchitectureWithViewModelExample()
                else -> Text("No example found.")
            }
        }
    }
}

// The ExampleList Composable that lists the items and navigates to the details screen
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleList(navController: NavController) {
    val examples = listOf(
        ExampleItem.SimpleComposableExample,
        ExampleItem.ComposeLifecycleExample,
        ExampleItem.BasicStateHandlingExample,
        ExampleItem.ConversionToStateExample,
        ExampleItem.HoistStateExample,
        ExampleItem.SideEffectExample,
        ExampleItem.LayoutExample,
        ExampleItem.ComposeArchitectureExample
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Examples") }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(examples) { example ->
                    Text(
                        text = example.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("detail/${example.name}")
                            }
                    )
                }
            }
        }
    }

}

// The AppNavigation setup remains the same, but calls ExampleDetailScreen with the argument
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ExampleList(navController)
        }
        composable("detail/{itemName}") { backStackEntry ->
            ExampleDetailScreen(
                backStackEntry.arguments?.getString("itemName") ?: "",
                navController::popBackStack
            )
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}