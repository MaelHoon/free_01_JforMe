package com.dodoojuice.jforme.place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentPlaceNearbyBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainItemListAdapter
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.VerticalItemDecorator

class PlaceNearbyFragment : Fragment() {
    lateinit var binding : FragmentPlaceNearbyBinding
    private var nearbyItemList = mutableListOf<MainRecyclerItem>()
    lateinit var nearbyRecyclerAdapter: MainItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceNearbyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }
    private fun initRecycler(){
        nearbyRecyclerAdapter = MainItemListAdapter(nearbyItemList)
        binding.recycler.apply {
            adapter = nearbyRecyclerAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(13))
            addItemDecoration(HorizontalItemDecorator(13))
        }
        nearbyItemList.apply {
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

}