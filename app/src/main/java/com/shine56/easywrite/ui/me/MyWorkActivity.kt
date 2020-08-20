package com.shine56.easywrite.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity
import com.shine56.easywrite.base.MyAdapter
import com.shine56.easywrite.databinding.ActivityMyWorkBinding
import com.shine56.easywrite.model.bean.ShortEssayWork
import com.shine56.easywrite.ui.square.ShortWorkActivity
import com.shine56.easywrite.util.logD
import com.shine56.easywrite.util.viewHelper.MyTransform
import com.shine56.easywrite.viewModel.SquareVm

class MyWorkActivity : BaseActivity() {

    private lateinit var binding: ActivityMyWorkBinding
    private lateinit var vm: SquareVm
    private lateinit var adapter: MyAdapter<ShortEssayWork>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_work)
        vm = ViewModelProvider(this)[SquareVm::class.java]
        initView()
        onObserve()
    }

    private fun onObserve(){
        vm.workList.observe(this, Observer {
            adapter.replaceAll(it)
        })
    }

    private fun initView(){
        resetStatusBar(BaseActivity.TRANSPARENT_BLACK)
        initRecy()
        vm.refreshData()
    }

    private fun initRecy(){
        val recy = binding.recyShareWork
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        //val space = 50 //间距

        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recy.layoutManager = layoutManager
        // recy.addItemDecoration(SpacesItemDecoration( MyApplication.VERTIVAL, space))

        adapter = MyAdapter(R.layout.list_work_2)
        adapter.setOnBindListener { list, holder, position ->
            val headImg = holder.itemView.findViewById<ImageView>(R.id.iv_work_author_head_img)
            val workImg = holder.itemView.findViewById<ImageView>(R.id.iv_work_img)
            val text = holder.itemView.findViewById<TextView>(R.id.tv_work_text)
            val tvName = holder.itemView.findViewById<TextView>(R.id.tv_work_author_name)
            val tvLoveCount = holder.itemView.findViewById<TextView>(R.id.tv_work_love_count)



            text.text = list[position].text

            tvName.text= list[position].authorName

            tvLoveCount.text = list[position].loveCount.toString()

            Glide.with(this)
                .load(list[position].headImgId)
                //.transform(MyTransform(it.listPhoto.context,it.listPhoto.measuredWidth.toFloat(),20f, false))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.color.mainBg2)
                .into(headImg)

            workImg.post {
                //workImg.width.toFloat().toString().logD("宽宽酷酷酷酷酷")
                Glide.with(this)
                    .load(list[position].photoId)
                    .apply(RequestOptions.bitmapTransform(MyTransform(workImg.context,workImg.width.toFloat(),2f, false)))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.color.mainBg2)
                    .into(workImg)
            }
        }
        adapter.setItemClickListener { view, position ->
            startActivity(ShortWorkActivity::class.java)
        }

        recy.adapter = adapter
    }
}