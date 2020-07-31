package com.shine56.easywrite.base

import android.app.Activity
import com.shine56.easywrite.util.logD
import java.util.*

object ActivityManager {
    private var activityList: MutableList<Activity> = ArrayList()

    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    fun finishActivity(name: String?) {
        if (name != null) {
            for (i in activityList.indices) {
                val activity = activityList[i]
                if (activity.javaClass.name == name) {
                    if (!activity.isFinishing) {
                        activity.finish()
                    }
                    activityList.remove(activity)
                }
            }
        }
    }

    fun finishAllActivity() {
        for (activity in activityList) {
           "finish活动栈中所有活动 -> ${activity.javaClass.simpleName}".logD()
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activityList.clear()
    }
}