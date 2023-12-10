package com.dodoojuice.jforme.main

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.MainActivity
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.RecyclerMainOtherListBinding
import com.dodoojuice.jforme.databinding.RecyclerMainTopListBinding

class MainCateListAdapter(val activity : Activity, var itemlist: MutableList<MainRecyclerItemList>, var viewModel: MainGpsViewModel): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener : OnClickInterface? = null
    interface OnClickInterface{
        fun onGpsClick()
        fun onChangeLocationClick()
    }
    fun setOnItemClickListener(listener : OnClickInterface){
        this.listener = listener
    }

    inner class ViewHolder1(private val binding: RecyclerMainTopListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.lifecycleOwner = activity as MainActivity
            binding.mainGpsViewModel = viewModel
        }
        fun bind(list: MainRecyclerItemList) {

            binding.title.text = list.cate
            if(list.cate != "제이미의 제안") {
                binding.jmeImg.visibility = View.GONE
                binding.changeLocation.setOnClickListener{
                    listener?.onChangeLocationClick()
                }
                binding.currentLocation.setOnClickListener{
                    listener?.onGpsClick()
                }
            }
            else {
                binding.changeLocation.visibility = View.GONE
                binding.currentLocation.visibility = View.GONE
                binding.currentLocationText.visibility = View.GONE
            }
            binding.itemList.apply {
                adapter = MainItemListAdapter(list.itemList)
                layoutManager = LinearLayoutManager(binding.itemList.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                addItemDecoration(HorizontalItemDecorator(10))
            }
        }
    }
    inner class ViewHolder2(private val binding: RecyclerMainOtherListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: MainRecyclerItemList) {

            binding.title.text = list.cate
            binding.itemList.apply {
                adapter = MainItemListAdapter(list.itemList)
                layoutManager = GridLayoutManager(binding.itemList.context, 3)
                setHasFixedSize(true)
                addItemDecoration(VerticalItemDecorator(13))
                addItemDecoration(HorizontalItemDecorator(13))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemlist[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> return ViewHolder1(
                DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                    R.layout.recycler_main_top_list,
                    parent,
                false))
            else -> return ViewHolder2(
                RecyclerMainOtherListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(itemlist[position].type){
            1-> {
                (holder as ViewHolder1).bind(itemlist[position])
                holder.setIsRecyclable(false)
            }
            else-> {
                (holder as ViewHolder2).bind(itemlist[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}