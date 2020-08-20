package com.shine56.easywrite.ui.longessay

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.base.MyAdapter
import com.shine56.easywrite.base.MyApplication
import com.shine56.easywrite.databinding.ActivityLongEssayCreateBinding
import com.shine56.easywrite.model.bean.LongPhoto
import com.shine56.easywrite.util.PermissionUtil
import com.shine56.easywrite.util.logD
import com.shine56.easywrite.util.toast
import com.shine56.easywrite.util.viewHelper.MyTransform
import com.shine56.easywrite.util.viewHelper.RichText
import com.shine56.easywrite.util.viewHelper.SpacesItemDecoration
import com.shine56.easywrite.viewModel.LongVm

class LongEssayCreateActivity : BaseActivity() {
    private lateinit var binding : ActivityLongEssayCreateBinding
    private lateinit var vm: LongVm
    private lateinit var adapter: MyAdapter<LongPhoto>
    private lateinit var addPhotoDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_long_essay_create)
        vm = ViewModelProvider(this)[LongVm::class.java]
        initView()
        initListener()
        onObserve()
    }
    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)
        val tvTitle = findViewById<TextView>(R.id.toolbar_second_title)
        tvTitle.text = "文章创作"
        initChoosePhotoDialog()

        val backIv = findViewById<ImageView>(R.id.toolbar_second_left)
        backIv.setOnClickListener {
            finish()
        }

        initRecy()
    }

    private fun initRecy(){
        val recy = binding.recyLongPhoto
        val layoutManager = LinearLayoutManager(this)
        val space = 50 //间距

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recy.layoutManager = layoutManager
        recy.addItemDecoration(SpacesItemDecoration( MyApplication.HORIZONTAL, space))

        adapter = MyAdapter(R.layout.list_long_photo)

        adapter.setOnBindListener { list, holder, position ->

            val workImg = holder.itemView.findViewById<ImageView>(R.id.iv_list_long_photo)

            workImg.post {
                Glide.with(this)
                    .load(list[position].path)
                    //.transform(MyTransform(workImg.context,workImg.width.toFloat(),20f, true))
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(50)))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.color.mainBg2)
                    .into(workImg)
            }
        }

        recy.adapter = adapter
    }

    private fun initListener(){

        binding.longAddPhoto.setOnClickListener {
            addPhotoDialog.show()
        }

        binding.switchLac.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val text = binding.etLongWrite.text.toString()
                if(text.isNotEmpty()){
                    vm.lac()
                }else{
                    "输入太短".logD()
                }
            }else{
                RichText(binding.etLongWrite).restoreText()
            }
        }

        binding.etLongWrite.setOnClickListener {
            startActivity(WebActivity::class.java)
        }

        binding.longSave.setOnClickListener {
            "保存成功".toast()
            finish()
        }
    }
    private fun onObserve(){
        vm.text.observe(this, Observer {
            RichText(binding.etLongWrite).setTextBg()
        })
        vm.photoList.observe(this, Observer {
            adapter.replaceAll(it)
        })
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
                startActivityForResult (intentAlbum, MyApplication.CHOOSE_PHOTO)
                addPhotoDialog.hide()
            }else{
                PermissionUtil.request(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MyApplication.CHOOSE_PHOTO
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MyApplication.TAKE_PHOTO -> if (resultCode == RESULT_OK) {
            }
            MyApplication.CHOOSE_PHOTO -> if (resultCode == RESULT_OK && data != null) {
                vm.addPhoto(data.data.toString())
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
            MyApplication.TAKE_PHOTO -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addPhotoDialog.hide()
            } else {
                showToast("没有权限无法正常使用相机哟")
            }
            MyApplication.CHOOSE_PHOTO -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intentAlbum = Intent(Intent.ACTION_GET_CONTENT)
                intentAlbum.type = "image/*"
                startActivityForResult(
                    intentAlbum,
                    MyApplication.CHOOSE_PHOTO
                )
                addPhotoDialog.hide()
            } else {
                showToast("没有权限无法正常使用相册照片哟")
            }
        }
    }


    /**
     * 中文分词
     */
    open external fun initLac(model_path: String?): Unit

    external fun releaseLac()

    external fun stringFromJNI(): String?

    external fun stringCutFromJNI(source_text: String?): String?
}