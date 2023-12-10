package com.dodoojuice.jforme.place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.community.CommunityRecyclerAdapter
import com.dodoojuice.jforme.community.CommunityRecyclerItem
import com.dodoojuice.jforme.databinding.FragmentPlaceCommunityBinding
import com.dodoojuice.jforme.main.VerticalItemDecorator

class PlaceCommunityFragment : Fragment() {
    lateinit var binding : FragmentPlaceCommunityBinding
    lateinit var recyclerAdapter: CommunityRecyclerAdapter
    private var itemList = mutableListOf<CommunityRecyclerItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }
    private fun initRecycler(){
        recyclerAdapter = CommunityRecyclerAdapter(itemList, this)
        binding.recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        val imgList = mutableListOf<Int>(R.drawable.pinkjheart, R.drawable.box_size, R.drawable.bell)
        val textLsit = mutableListOf<String>("안뇽", "하세용", "!!!")
        //item 추가
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, true, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, false, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, false, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, true, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, true, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, true, 15, 2, textLsit))
        itemList.add(CommunityRecyclerItem(R.drawable.j_profile, "신영이씨", "신ㄴ영이가 자꾸 나를 귀찮게 굴허", imgList, true, 15, 2, textLsit))

    }
}