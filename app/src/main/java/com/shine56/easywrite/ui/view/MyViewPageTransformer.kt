package com.shine56.easywrite.ui.view


import android.view.View
import androidx.viewpager.widget.ViewPager

class MyViewPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        if (position <= 1) {
            val scaleFactor =
                MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE)
            page.scaleX = scaleFactor
            if (position > 0) {
                page.translationX = -scaleFactor * 2
            } else if (position < 0) {
                page.translationX = scaleFactor * 2
            }
            page.scaleY = scaleFactor
        } else {
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE
        }
    }

    companion object {
        private const val MAX_SCALE = 1f
        private const val MIN_SCALE = 0.95f
    }
}