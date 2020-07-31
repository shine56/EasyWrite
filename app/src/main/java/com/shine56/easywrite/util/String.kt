package com.shine56.easywrite.util

import android.widget.Toast
import com.shine56.easywrite.base.MyApplication

fun String.toast(){
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_SHORT).show()
}

fun String.logD(){
    LogUtil.logD(this)
}
fun String.logD(tag: String){
    LogUtil.logD(tag,this)
}

fun String.logE(tag: String){
    LogUtil.logE(tag,this)
}