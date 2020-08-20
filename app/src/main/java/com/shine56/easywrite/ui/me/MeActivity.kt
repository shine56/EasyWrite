package com.shine56.easywrite.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity

class MeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_me)

        initView()
    }

    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)
    }
}