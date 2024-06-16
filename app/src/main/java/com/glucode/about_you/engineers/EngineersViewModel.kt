package com.glucode.about_you.engineers

import androidx.lifecycle.ViewModel
import com.glucode.about_you.mockdata.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EngineersViewModel: ViewModel() {

    private var _engineers = MutableStateFlow(MockData.engineers)
    val engineers = _engineers.asStateFlow()

    fun sortByYears() {
        _engineers.value = MockData.engineers.sortedBy { it.quickStats.years }.toMutableList()
    }

    fun sortByCoffees() {
        _engineers.value = MockData.engineers.sortedBy { it.quickStats.coffees }.toMutableList()
    }

    fun sortByBugs() {
        _engineers.value = MockData.engineers.sortedBy { it.quickStats.bugs }.toMutableList()
    }

}
