package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Composable
fun ConversionToStateExample(
    viewModel: MyViewModel = hiltViewModel()
) {
    // LiveData to State
    val liveDataState = viewModel.liveData.observeAsState("")

    // StateFlow to State
    val stateFlowState = viewModel.stateFlow.collectAsState()

    Column {
        Text("LiveData state: ${liveDataState.value}")
        Button(onClick = { viewModel.updateLiveData() }) {
            Text("Update LiveData")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("StateFlow state: ${stateFlowState.value}")
        Button(onClick = { viewModel.updateStateFlow() }) {
            Text("Update StateFlow")
        }
    }
}
@HiltViewModel
class MyViewModel @Inject constructor(): ViewModel() {
    val liveData = MutableLiveData<String>("Hello from LiveData")
    val stateFlow = MutableStateFlow("Hello from StateFlow")

    fun updateLiveData() {
        liveData.value = "Updated LiveData: ${System.currentTimeMillis()}"
    }

    fun updateStateFlow() {
        stateFlow.update { "Updated StateFlow: ${System.currentTimeMillis()}" }
    }
}
