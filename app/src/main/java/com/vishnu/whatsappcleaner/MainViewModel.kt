package com.vishnu.whatsappcleaner

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val contentResolver = application.contentResolver

    private val storeData = StoreData(application.applicationContext)

    fun listDirectories(path: String): MutableLiveData<ArrayList<String>> {
        Log.i("vishnu", "listDirectories: $path")

        val list = ArrayList<String>()

        val mutableLiveData = MutableLiveData<ArrayList<String>>()

        viewModelScope.launch {
            File(path).list { dir, name -> list.add("$dir/$name") }

            Log.i("vishnu", "listDirectories: $list")
            mutableLiveData.postValue(list)
        }

        return mutableLiveData;
    }

    fun saveHomeUri(path: String) {
        Log.i("vishnu", "saveHomeUri: $path")
        viewModelScope.launch {
            storeData.set(
                Constants.WHATSAPP_HOME_URI,
                "/storage/emulated/0/" + path
            )
        }
    }

    fun getHomeUri(): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        viewModelScope.launch {
            mutableLiveData.postValue(storeData.get(Constants.WHATSAPP_HOME_URI))
        }
        return mutableLiveData;
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