package com.shine56.easywrite.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.shine56.easywrite.R

class HomePageAdapter: PagerAdapter() {

    private var itemClick: ((container: ViewGroup, position: Int) -> Unit)? = null
    fun setItemClickListener(listener: (container: ViewGroup, position: Int) -> Unit){
        itemClick = listener
    }


    private val viewList = listOf(
        R.mipmap.short_essay,
        R.mipmap.long_essay,
        R.mipmap.share_space,
        R.mipmap.material_space
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` is View) {
            container.removeView(`object`)
        }
    }

    override fun getPageWidth(position: Int): Float {
        return 0.7f
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.list_home_view_page, container, false)
        val imageView = view.findViewById<ImageView>(R.id.home_vp_item_image)
        val cardView = view.findViewById<CardView>(R.id.home_vp_item_card)
        val resource = container.context.resources

        when(position){
            0 -> {
                cardView.setCardBackgroundColor(resource.getColor(R.color.bg_1))
            }
            1 -> {
                cardView.setCardBackgroundColor(resource.getColor(R.color.bg_2))
            }
            2 -> {
                cardView.setCardBackgroundColor(resource.getColor(R.color.bg_3))
            }
            3 -> {
                cardView.setCardBackgroundColor(resource.getColor(R.color.bg_4))
            }
        }

        Glide.with(container.context)
            .load(viewList[position])
            .error(R.color.mainBg2)
            .into(imageView)

        imageView.setOnClickListener {
            itemClick?.invoke(container, position)
        }


        container.addView(view)
        return view
    }
}