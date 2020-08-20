package com.shine56.easywrite.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity

class FeedBackActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)
        initView()
    }

    private fun initView() {

        resetStatusBar(TRANSPARENT_BLACK)
        val tvTitle = findViewById<TextView>(R.id.toolbar_second_title)
        tvTitle.text = "我要反馈"
    }
}