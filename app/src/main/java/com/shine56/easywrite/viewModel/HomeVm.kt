package com.shine56.easywrite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseViewModel
import com.shine56.easywrite.model.bean.ShortEssayWork

class HomeVm : BaseViewModel(){
    val workList = refreshLiveData.switchMap { execute {
        getTodayWorks()
    } }
    private fun getTodayWorks(): List<ShortEssayWork> {
        val list = mutableListOf<ShortEssayWork>()

        list.add(ShortEssayWork(photoId = R.drawable.work3, text = "我见青山多妩媚，料青山，见我应如是。\n\n——辛弃疾《贺新郎》", headImgId = R.mipmap.header_ima, loveCount = 218, authorName = "Alice"))

        list.add(ShortEssayWork(photoId = R.drawable.work2, text = "流光容易把人抛。红了樱桃。绿了芭蕉。\n\n——蒋捷《一剪梅·舟过吴江》", headImgId = R.mipmap.header_img_2, loveCount = 180, authorName = "我不是猪"))
        list.add(ShortEssayWork(photoId = R.drawable.work1, text = "应是天仙狂醉，乱把白云揉碎。\n\n——李白《清平乐·画堂晨起》", headImgId = R.mipmap.header_img_3, loveCount = 120, authorName = "大群"))

        return list
    }
}