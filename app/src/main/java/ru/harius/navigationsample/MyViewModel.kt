package ru.harius.navigationsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModel(
    private val _openScreenB: () -> Unit
) : ViewModel() {
    fun openScreenB() {
        _openScreenB()
    }
}

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val openScreenB: () -> Unit) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(openScreenB) as T
    }
}