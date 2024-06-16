package com.glucode.about_you.about

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel: ViewModel() {
    private var _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()


    private var _engineerName = MutableStateFlow("")

    fun updateProfileImage(uri: Uri) {
        _imageUri.value = uri
        val engineer = MockData.engineers.first { it.name == _engineerName.value }
        val updateEngineerImage = engineer.copy(defaultImageName = uri.toString())

        val index = MockData.engineers.indexOfFirst { it.name == _engineerName.value }

        if (index != -1) {
            MockData.engineers[index] = updateEngineerImage
        } else {
            println("Engineer ${_engineerName.value} not found.")
        }
    }

    fun updateEngineerName(name: String) {
        _engineerName.value = name
    }

    fun getEngineerName() = _engineerName.value
}
