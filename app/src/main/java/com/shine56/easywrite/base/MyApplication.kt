package com.shine56.easywrite.base

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object{
        lateinit var context: Context

        const val IS_ABANDON = 1
        const val NOT_ABANDON = 0

        const val IS_FINISH = 1
        const val NOT_FINISH = 0

        const val FILM = "Film"
        const val MUSIC = "Music"
        const val WALLPAPER = "Wallpaper"

        const val AUTO = 0
        const val ZH_CN = 1
        const val ZH_HK = 2
        const val EN = 3

        const val HAPPY = 1
        const val CALM = 2
        const val COMPLEX = 3
        const val SAD = 4
        const val AI = 5

        const val SAD_START = -50
        const val COMPLEX_START = -30
        const val CALM_START = -10
        const val HAPPY_START = 20
        const val HAPPY_END = 51

        const val TASK_1 = 1
        const val TASK_2 = 2
        const val TASK_3 = 3
        const val TASK_4 = 4
        const val TASK_TODAY = 5
        const val TASK_MARK = 6

        const val VERTIVAL = 1
        const val HORIZONTAL = 2
        const val TAKE_PHOTO = 3
        const val CHOOSE_PHOTO = 4
        const val LOCATION = 5
        const val LOCATION_2 = 6
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}