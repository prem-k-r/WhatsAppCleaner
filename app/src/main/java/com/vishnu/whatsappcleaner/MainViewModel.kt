package com.vishnu.whatsappcleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    val contentResolver = application.contentResolver

    fun listDirectories(path: String): MutableLiveData<ArrayList<String>> {

        val list = ArrayList<String>()

        val directoryList = MutableLiveData<ArrayList<String>>()

        viewModelScope.launch {
            File(path).list { dir, name -> list.add(name) }

            directoryList.postValue(list)
        }

        return directoryList;
    }

}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}