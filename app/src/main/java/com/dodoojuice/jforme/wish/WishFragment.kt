package com.dodoojuice.jforme.wish

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.community.CommunityRecyclerItem
import com.dodoojuice.jforme.databinding.FragmentWishBinding
import com.dodoojuice.jforme.databinding.RecyclerCommunityItemBinding
import com.dodoojuice.jforme.databinding.RecyclerMainItemBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainItemListAdapter
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.place.PlaceActivity

data class WishRecentRecyclerItem(var title : String, var img : Int)
class WishFragment : Fragment() {
    lateinit var binding : FragmentWishBinding
    private var likeItemList = mutableListOf<MainRecyclerItem>()
    private var recentItemList = mutableListOf<WishRecentRecyclerItem>()
    lateinit var recentRecyclerAdapter : WishRecentRecyclerAdapter
    lateinit var likeRecyclerAdapter: MainItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler(){
        //상단 최근 본 장소 리스트
        recentRecyclerAdapter = WishRecentRecyclerAdapter(recentItemList)
        binding.recentRecycler.apply {
            adapter = recentRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(HorizontalItemDecorator(10))
        }
        recentItemList.apply {
            add(WishRecentRecyclerItem("멈춰", R.drawable.box_size))
            add(WishRecentRecyclerItem("그만", R.drawable.box_size))
            add(WishRecentRecyclerItem("ㅠㅠㅜㅠㅜㅠㅜㅠㅜddkdkdkkdkdkdkdkdkdkkdkdkdkdkdk", R.drawable.box_size))
            add(WishRecentRecyclerItem("멈춰", R.drawable.box_size))
            add(WishRecentRecyclerItem("멈춰", R.drawable.box_size))
        }

        //하단 좋아요 표시한 장소 리스트
        likeRecyclerAdapter = MainItemListAdapter(likeItemList)
        binding.likeRecycler.apply {
            adapter = likeRecyclerAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(13))
            addItemDecoration(HorizontalItemDecorator(13))
        }
        likeItemList.apply {
            add(MainRecyclerItem("맛집이에유~", R.drawable.box_size, false))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size, false))
            add(MainRecyclerItem("맛집이에유~", R.drawable.box_size, false))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size, false))
            add(MainRecyclerItem("맛집이에유~", R.drawable.box_size, false))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size, false))
            add(MainRecyclerItem("맛집이에유~", R.drawable.box_size, false))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("여기저기요기조기", R.drawable.box_size, true))
            add(MainRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size, false))
        }
    }
    //최근 목록에 장소 item 추가
    fun addRecentItem(item : WishRecentRecyclerItem){
        recentItemList.add(item)
        recentRecyclerAdapter.notifyItemInserted(0)
    }
}

class WishRecentRecyclerAdapter(var itemlist: MutableList<WishRecentRecyclerItem>): RecyclerView.Adapter<WishRecentRecyclerAdapter.Holder>() {

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
        fun bind(data: WishRecentRecyclerItem, position: Int) {
            binding.place.text = data.title
            binding.mainImg.setImageResource(data.img)
            binding.miniImg.setBackgroundResource(R.drawable.trash)
            binding.miniImg.setOnClickListener{
                itemlist.removeAt(position)
                notifyItemRemoved(position) //쓰레기통 이미지 누르면 삭제
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position], position)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}
