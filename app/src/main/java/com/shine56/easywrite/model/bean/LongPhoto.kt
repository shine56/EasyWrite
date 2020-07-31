package com.shine56.easywrite.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

data class LongPhoto(
     var id: Int = 0,

     var diaryId: Int = -1,

     var type: Int = 1,

     var path: String = ""
)