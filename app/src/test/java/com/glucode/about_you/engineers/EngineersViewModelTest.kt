package com.glucode.about_you.engineers

import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EngineersViewModelTest {

    private lateinit var engineersViewModel: EngineersViewModel

    @Before
    fun setup() {
        engineersViewModel = EngineersViewModel()
    }

    @Test
    fun `testMenuSortByYears`() = runBlocking {
        // Given
        val unsortedEngineers = MockData.engineers

        // When
        engineersViewModel.sortByYears()

        // Then
        val sortedEngineers = engineersViewModel.engineers.first()
        assertEquals(unsortedEngineers.sortedBy { it.quickStats.years }, sortedEngineers)
    }

    @Test
    fun `testMenuSortByCoffees()`() = runBlocking {
        // Given
        val unsortedEngineers = MockData.engineers

        // When
        engineersViewModel.sortByCoffees()

        // Then
        val sortedEngineers = engineersViewModel.engineers.first()
        assertEquals(unsortedEngineers.sortedBy { it.quickStats.coffees }, sortedEngineers)
    }

    @Test
    fun `testMenuSortByBugs`() = runBlocking {
        // Given
        val unsortedEngineers = MockData.engineers

        // When
        engineersViewModel.sortByBugs()

        // Then
        val sortedEngineers = engineersViewModel.engineers.first()
        assertEquals(unsortedEngineers.sortedBy { it.quickStats.bugs }, sortedEngineers)
    }
}
