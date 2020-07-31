package com.shine56.easywrite.base

import androidx.lifecycle.*
import com.shine56.easywrite.util.logD
import kotlinx.coroutines.*

open class BaseViewModel(): ViewModel() {
    protected val refreshLiveData = MutableLiveData<Int>()

    open fun refreshData(){
        refreshLiveData.value = refreshLiveData.value
    }

    fun <T>execute(block: () -> T): LiveData<T> {
        return liveData(viewModelScope.coroutineContext+ Dispatchers.IO) {
            emit(block())
        }
    }
    fun <T>asyncExecute(block: () -> T): Deferred<T> {
        return viewModelScope.async(Dispatchers.IO){
            block()
        }
    }

    open fun onSuccess(requestCode: Int){}
    open fun onFail(e: Exception){}

    override fun onCleared() {
        "${javaClass.name}.clear()".logD()
        super.onCleared()
    }
}