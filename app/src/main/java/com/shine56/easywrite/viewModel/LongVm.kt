package com.shine56.easywrite.viewModel

import androidx.lifecycle.MutableLiveData
import com.shine56.easywrite.base.BaseViewModel
import com.shine56.easywrite.model.bean.LongPhoto
import com.shine56.easywrite.util.toast

class LongVm: BaseViewModel() {

    val text = MutableLiveData<String>()

    fun lac(){
        text.value = text.value
    }

    val photoList = MutableLiveData<MutableList<LongPhoto>>()

    init {
        photoList.value = mutableListOf()
    }

    fun addPhoto(str: String){
        val photo = LongPhoto(path = str)
        photoList.value?.add(photo)
        photoList.value = photoList.value
    }
}