package com.example.composestudy

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/*
    state 예제, https://www.youtube.com/watch?v=mymWGMy9pYI&t=265s
    following 중.

    아래와 같은 이슈가 발생함. library 누락 혹은 버전 문제로 생각됨.

 */

class HelloViewModel : ViewModel() {
    // 예제에서는, String!인데 !들어가면 에러 나옴.
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}

@Composable
fun HelloContentScreen(helloViewModel: HelloViewModel = viewModel()) {
    // implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    // https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=ko
    // viewModel()과 HelloViewModel()의 차이는 뭘까?? 생명주기??

    // https://developer.android.com/jetpack/androidx/releases/lifecycle?hl=ko#lifecycle-viewmodel-compose-1.0.0-alpha07
    // 이제 viewModel()에서 ViewModelStoreOwner를 사용하는 것은 선택사항이므로 LocalViewModelStoreOwner가 아닌 소유자와 더 쉽게 작업할 수 있습니다. 예를 들어, 이제 viewModel(navBackStackEntry)를 사용하여 특정 탐색 그래프와 연결된 ViewModel을 가져올 수 있습니다. (I2628d, b/188693123)


    val name by helloViewModel.name.observeAsState("")
    // https://stackoverflow.com/questions/66560404/jetpack-compose-unresolved-reference-observeasstate
    // implementation "androidx.compose.runtime:runtime-livedata:$compose_version"


    HelloContent(name, onNameChange = { helloViewModel.onNameChange(it) })


}


@Composable
fun HelloContent(name: String = "", onNameChange: (String) -> Unit = {}) {

    Column {
        Text("Hello, $name")
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}




@Preview(showBackground = false)
@Composable
fun HelloContentPreview() {
    HelloContentScreen()
}
