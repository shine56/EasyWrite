package com.shine56.easywrite.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initView()
    }

    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)

        binding.goSignUp.setOnClickListener {
            startActivity(SignUpActivity::class.java)
        }
    }
}