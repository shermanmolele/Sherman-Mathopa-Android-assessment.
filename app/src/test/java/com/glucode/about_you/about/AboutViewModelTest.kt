package com.glucode.about_you.about

import android.net.Uri
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class AboutViewModelTest {

    private lateinit var aboutViewModel: AboutViewModel

    @Before
    fun setup() {
        aboutViewModel = AboutViewModel()
    }

    @Ignore()
    @Test
    fun `test updateProfileImage should update imageUri and MockData`() = runBlocking {

        val uri = Uri.parse("content://path/to/image.jpg")
        val engineerName = "Big same"
        aboutViewModel.updateEngineerName(engineerName)

        aboutViewModel.updateProfileImage(uri)

        val updatedUri = aboutViewModel.imageUri.first()
        assertEquals(uri, updatedUri)

        val updatedEngineer = MockData.engineers.firstOrNull { it.name == engineerName }
        assertEquals(uri.toString(), updatedEngineer?.defaultImageName)
    }

    @Test
    fun `test update engineerName`() = runBlocking {

        val newName = "Big same"

        aboutViewModel.updateEngineerName(newName)

        val updatedName = aboutViewModel.getEngineerName()
        assertEquals(newName, updatedName)
    }
}
