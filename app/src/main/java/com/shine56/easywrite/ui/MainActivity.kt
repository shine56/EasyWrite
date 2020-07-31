package com.shine56.easywrite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.ui.home.HomeActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(HomeActivity::class.java)
    }
}