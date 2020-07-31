package com.shine56.easywrite.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shine56.easywrite.base.BaseViewModel
import com.shine56.easywrite.model.bean.LongPhoto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShortVm : BaseViewModel() {
    val photo = MutableLiveData<String>()

    val matchControl = MutableLiveData<Int>()

    fun match(type: Int){
        viewModelScope.launch {
            delay(1000)
            matchControl.value = type
        }
    }
}
