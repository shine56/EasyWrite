package com.shine56.easywrite.util.viewHelper

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.widget.EditText
import com.shine56.easywrite.util.toast

class RichText(private val editText: EditText) {

    /**
     * 文字背景颜色
     */
    fun setTextBg(){

        //默认实现
        "抱歉，智能小AI出现一点小问题了。".toast()
//        val editable = editText.editableText
//        editable.clear()
//        editable.append(setBackgroundColorSpan("辛亥革命"))
//        editable.append("作为一场近现代社会发展进程中引人关注的事件,让人不得不有所感触。\n\n")
//        editable.append("辛亥革命是一场成功的革命事件，这为以后的")
//        editable.append(setBackgroundColorSpan("新文化运动"))
//        editable.append("、对国民性进行改造的思想观念的提出，包括")
//        editable.append(setBackgroundColorSpan("三民主义"))
//        editable.append("、")
//        editable.append(setBackgroundColorSpan("社会主义"))
//        editable.append("道路的探索都创造了较宽松的社会环境，是对旧有的社会体制的改观，是对")
//        editable.append(setBackgroundColorSpan("鸦片战争"))
//        editable.append("以来，陷入帝国主义侵略和封建压迫的深重痛苦中的中国人带来了一点希望的曙光。")
    }
    fun restoreText(){
        val str = editText.text.toString()
        editText.text.clear()
        editText.text.append(str)
    }


    fun setBackgroundColorSpan(string: String): SpannableString{
        val spannableString = SpannableString(string)
        val colorSpan = BackgroundColorSpan(Color.parseColor("#302196F3"))
        spannableString.setSpan(
            colorSpan,
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannableString

    }
}