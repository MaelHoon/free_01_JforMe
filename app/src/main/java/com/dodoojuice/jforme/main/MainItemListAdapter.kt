package com.dodoojuice.jforme.main

import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.RecyclerMainItemBinding
import com.dodoojuice.jforme.place.PlaceActivity

class MainItemListAdapter(var itemlist: MutableList<MainRecyclerItem>): RecyclerView.Adapter<MainItemListAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerMainItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, PlaceActivity::class.java)
                ContextCompat.startActivity(binding.root.context, intent, null)
            }
            binding.place.setOnClickListener{
                val intent = Intent(binding.root.context, PlaceActivity::class.java)
                ContextCompat.startActivity(binding.root.context, intent, null)
            }
        }
        fun bind(data: MainRecyclerItem) {
            binding.place.text = data.title
            binding.mainImg.setImageResource(data.mainImg)
            if(data.isLikeChecked) binding.miniImg.setBackgroundResource(R.drawable.pinkheart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}


class VerticalItemDecorator(private val divHeight : Int) : RecyclerView.ItemDecoration() {

    @Override
    override fun getItemOffsets(outRect: Rect, view: View, parent : RecyclerView, state : RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = divHeight
        outRect.bottom = divHeight
    }
}
class HorizontalItemDecorator(private val divHeight : Int) : RecyclerView.ItemDecoration() {

    @Override
    override fun getItemOffsets(outRect: Rect, view: View, parent : RecyclerView, state : RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = divHeight
        outRect.right = divHeight
    }
}