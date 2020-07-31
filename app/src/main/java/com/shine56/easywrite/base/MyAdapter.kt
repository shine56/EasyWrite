package com.shine56.easywrite.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class MyAdapter <T>(
    @LayoutRes
    private val layoutId: Int
) : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var onCreate : ((layoutId: Int, inflater: LayoutInflater, parent: ViewGroup) -> View)? = null
    private lateinit var onBind: (list: MutableList<T>, holder: MyHolder, position: Int) -> Unit
    private var itemClick: ((view: View, position: Int) -> Unit)? = null
    private val list: MutableList<T> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = if(onCreate == null){
            LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
        }else{
            onCreate!!(layoutId, LayoutInflater.from(parent.context), parent)
        }

        val holder = MyHolder(itemView)
        itemView.setOnClickListener {
            itemClick?.invoke(it, holder.layoutPosition)
        }
        return MyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        onBind.invoke(list, holder, position)
    }

    fun setItemClickListener(listener: (view: View, position: Int) -> Unit) {
        this.itemClick = listener
    }

    fun setOnCreateListener(listener: (layoutId: Int, inflater: LayoutInflater, parent: ViewGroup) -> View){
        this.onCreate = listener
    }

    fun setOnBindListener(listener: (list: MutableList<T>, holder: MyHolder, position: Int) -> Unit) {
        this.onBind = listener
    }

    fun replaceAll(data: List<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}