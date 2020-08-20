package com.shine56.easywrite.ui.home


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
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
import com.shine56.easywrite.base.MyApplication.Companion.VERTIVAL
import com.shine56.easywrite.databinding.ActivityHomeBinding
import com.shine56.easywrite.model.bean.ShortEssayWork
import com.shine56.easywrite.ui.longessay.LongEssayCreateActivity
import com.shine56.easywrite.ui.me.*
import com.shine56.easywrite.ui.shortessay.ShortEssayCreateActivity
import com.shine56.easywrite.ui.square.SquareActivity
import com.shine56.easywrite.ui.view.MyViewPageTransformer
import com.shine56.easywrite.util.viewHelper.MyTransform
import com.shine56.easywrite.util.viewHelper.SpacesItemDecoration
import com.shine56.easywrite.viewModel.HomeVm
import kotlinx.android.synthetic.main.layout_me_header.*

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var vm: HomeVm
    private lateinit var adapter: MyAdapter<ShortEssayWork>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        vm = ViewModelProvider(this).get(HomeVm::class.java)
        initView()
        onObserve()
        vm.refreshData()
    }
    private fun initView(){
        resetStatusBar(TRANSPARENT_BLACK)
        initViewPage()
        initRecy()


        val userHeadImg = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.user_head_img)
        Glide.with(this)
            .load(R.drawable.head_example)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(500)))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.color.hint_white)
            .into(userHeadImg)

        binding.homeToolbarLeft.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.work -> startActivity(MyWorkActivity::class.java)
                R.id.log_out -> startActivity(LoginActivity::class.java)
                R.id.feed_back -> startActivity(FeedBackActivity::class.java)
                R.id.setting -> startActivity(SettingActivity::class.java)
                R.id.about -> startActivity(AboutActivity::class.java)
            }

            true
        }
    }
    private fun onObserve(){
        vm.workList.observe(this, Observer {
            adapter.replaceAll(it)
        })
    }
    private fun initViewPage(){
        val viewPage = binding.homeVp
        val adapter = HomePageAdapter()
        viewPage.adapter = adapter
        viewPage.offscreenPageLimit = 3
        viewPage.pageMargin = 20
        viewPage.setPageTransformer(false,MyViewPageTransformer())

        adapter.setItemClickListener { container, position ->
            when(position){
                0 -> {
                    startActivity(ShortEssayCreateActivity::class.java)
                }
                1 -> {
                    startActivity(LongEssayCreateActivity::class.java)
                }
                2 -> {
                    startActivity(SquareActivity::class.java)
                }
            }
        }
    }

    private fun initRecy(){
        val recy = binding.recyTodayMaterial
        val layoutManager = LinearLayoutManager(this)
        val space = 50 //间距

        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recy.layoutManager = layoutManager
        recy.addItemDecoration(SpacesItemDecoration( VERTIVAL, space))

        adapter = MyAdapter(R.layout.list_work)
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
                Glide.with(this)
                    .load(list[position].photoId)
                    .transform(MyTransform(workImg.context,workImg.width.toFloat(),0f, false))
                    //.apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.color.mainBg2)
                    .into(workImg)
            }
        }

        recy.adapter = adapter
    }
}