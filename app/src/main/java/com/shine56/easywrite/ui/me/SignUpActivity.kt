package com.shine56.easywrite.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()
    }

    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)
    }
}