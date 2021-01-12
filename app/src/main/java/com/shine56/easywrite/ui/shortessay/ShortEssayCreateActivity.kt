package com.shine56.easywrite.ui.shortessay

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.base.MyApplication.Companion.CHOOSE_PHOTO
import com.shine56.easywrite.base.MyApplication.Companion.TAKE_PHOTO
import com.shine56.easywrite.databinding.ActivityShortEssayCreateBinding
import com.shine56.easywrite.util.PermissionUtil
import com.shine56.easywrite.util.toast
import com.shine56.easywrite.util.viewHelper.MyTransform
import com.shine56.easywrite.viewModel.ShortVm

class ShortEssayCreateActivity : BaseActivity() {

    private lateinit var binding : ActivityShortEssayCreateBinding
    private lateinit var addPhotoDialog: Dialog
    private lateinit var vm: ShortVm
    private lateinit var matchDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_short_essay_create)
        binding.activity = this
        vm = ViewModelProvider(this)[ShortVm::class.java]

        initView()
        onObserve()
    }

    private fun onObserve(){
        vm.photo.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .transform(MyTransform(this, binding.ivShotImg.width.toFloat(), 0f, false))
                .into(binding.ivShotImg)
        })

        vm.matchControl.observe(this, Observer {
            if (it == 1){
//                Glide.with(this)
//                    .load(R.drawable.work3)
//                    .transform(MyTransform(this, binding.ivShotImg.width.toFloat(), 0f, false))
//                    .into(binding.ivShotImg)
                "抱歉，服务器出现一点小问题了。".toast()
            }else{
                "抱歉，出现了一点小问题，未匹配到相关文案。".toast()
//                binding.etShortText.setText("人不是因为没有信念而失败，而是因为不能把信念化成行动，并且坚持到底。\n" +
//                        "\n" +
//                        "--卡耐基")
            }
            matchDialog.hide()
        })
    }

    fun onClick(v: View){
        when(v.id){
            R.id.add_photo ->{
                addPhoto()
            }
            R.id.add_words ->{
                addWords()
            }
            R.id.short_share ->{
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(vm.photo.value))
                shareIntent.putExtra("Kdescription", binding.etShortText.text.toString());
                shareIntent.type = "image/jpg"
                startActivity(Intent.createChooser(shareIntent, "Share to..."))
            }
            R.id.short_save ->{
                "保存成功".toast()
                finish()
            }
        }
    }
    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)
        initChoosePhotoDialog()
        initMatchDialog()

        val backIv = findViewById<ImageView>(R.id.toolbar_second_left)
        backIv.setOnClickListener {
            finish()
        }

        binding.ivShotImg.setOnClickListener {
            addPhotoDialog.show()
        }
    }

    private fun addPhoto(){
        matchDialog.show()

        vm.match(1)

    }
    private fun addWords(){
        matchDialog.show()

        vm.match(2)
    }

    private fun initChoosePhotoDialog() {
        addPhotoDialog = Dialog(this, R.style.BottomDialog)
        //填充对话框的布局
        val inflate = LayoutInflater.from(this)
        val view = inflate.inflate(R.layout.dialog_choose_photo, null, false)

        //将布局设置给Dialog
        addPhotoDialog.setContentView(view)
        //获取当前Activity所在的窗体
        val dialogWindow = addPhotoDialog.window
        //设置Dialog从窗体底部弹出
        dialogWindow!!.setGravity(Gravity.BOTTOM)
        //获得窗体的属性
        val lp = dialogWindow.attributes
        lp.y = 0 //设置Dialog距离底部的距离
        //宽度填满
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        //将属性设置给窗体
        dialogWindow.attributes = lp

        val cameraBt = view.findViewById<Button>(R.id.take_photo)
        val albumBt = view.findViewById<Button>(R.id.album)
        val cancelBt = view.findViewById<Button>(R.id.cancel_add_photo)

        albumBt.setOnClickListener {

            addPhotoDialog.hide()

            if (PermissionUtil.check(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                val intentAlbum = Intent(Intent.ACTION_GET_CONTENT)
                intentAlbum.type = "image/*"
                startActivityForResult (intentAlbum, CHOOSE_PHOTO)
                addPhotoDialog.hide()
            }else{
                PermissionUtil.request(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, CHOOSE_PHOTO)
            }
        }
    }

    private fun initMatchDialog() {
        val builder =  AlertDialog.Builder(this)
        val view =
            LayoutInflater.from(this).inflate(R.layout.dialog_progressbar, null, false)
        builder.setView(view)
        val title = view.findViewById<TextView>(R.id.dialog_pro_text)
        title.text = "智能小AI正在为您匹配..."

        matchDialog = builder.create()
        matchDialog.setCanceledOnTouchOutside(false)
        matchDialog.setCancelable(false)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> if (resultCode == RESULT_OK) {
            }
            CHOOSE_PHOTO -> if (resultCode == RESULT_OK && data != null) {
                vm.photo.value = data.data.toString()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            TAKE_PHOTO -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addPhotoDialog.hide()
            } else {
                showToast("没有权限无法正常使用相机哟")
            }
            CHOOSE_PHOTO -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intentAlbum = Intent(Intent.ACTION_GET_CONTENT)
                intentAlbum.type = "image/*"
                startActivityForResult(
                    intentAlbum,
                    CHOOSE_PHOTO
                )
                addPhotoDialog.hide()
            } else {
                showToast("没有权限无法正常使用相册照片哟")
            }
        }
    }

}