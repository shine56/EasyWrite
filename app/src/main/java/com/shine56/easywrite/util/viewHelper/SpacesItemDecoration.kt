package com.shine56.easywrite.util.viewHelper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shine56.easywrite.base.MyApplication

class SpacesItemDecoration(
    private val orientation: Int,
    private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        //设置item之间的间距（上下左右）

        if (orientation == MyApplication.VERTIVAL) {
            //if (parent.getChildLayoutPosition(view) === 0) outRect.top = space
            outRect.bottom = space
        } else {
//            if (parent.getChildLayoutPosition(view) != 0) {
//
//            }
            outRect.right = space
        }
    }
}