package com.shine56.easywrite.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shine56.easywrite.base.MyApplication

object PermissionUtil {
    /**
     * 检查权限
     */
    fun check(permission : String) : Boolean{
        //申请权限
        return ContextCompat.checkSelfPermission(MyApplication.context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 申请权限
     */
    fun request(activity: Activity, permission : String, requestCode : Int){
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }
}