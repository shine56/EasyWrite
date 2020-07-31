package com.shine56.easywrite.ui.square

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.databinding.ActivityShortWorkBinding
import com.shine56.easywrite.util.viewHelper.MyTransform

class ShortWorkActivity : BaseActivity() {

    private lateinit var binding: ActivityShortWorkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_short_work)

        initView()
    }

    fun initView(){

        resetStatusBar(TRANSPARENT_BLACK)

        Glide.with(this)
            .load(R.mipmap.header_ima)
            //.transform(MyTransform(it.listPhoto.context,it.listPhoto.measuredWidth.toFloat(),20f, false))
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.color.mainBg2)
            .into(binding.shortWorkAuthorImg)

        val toolTitle = findViewById<TextView>(R.id.toolbar_second_title)
        toolTitle.text = ""

        binding.shortWorkImg.setImageResource(R.drawable.cat)



        binding.shortWorkText.text = "我的童年，和这只猫儿一样，眷恋在这条小巷中。"

        val backIv = findViewById<ImageView>(R.id.toolbar_second_left)
        backIv.setOnClickListener {
            finish()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

//        binding.shortWorkImg.post {
//            Glide.with(this)
//                .load(R.drawable.cat)
//                .transform(MyTransform(this,binding.shortWorkImg.width.toFloat(),0f, false))
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .error(R.color.mainBg2)
//                .into(binding.shortWorkImg)
//        }
    }
}