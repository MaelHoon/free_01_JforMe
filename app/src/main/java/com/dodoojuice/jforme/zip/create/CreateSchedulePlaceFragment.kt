package com.dodoojuice.jforme.zip.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCreateSchedulePlaceBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainItemListAdapter
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.wish.WishRecentRecyclerAdapter
import com.dodoojuice.jforme.wish.WishRecentRecyclerItem


class CreateSchedulePlaceFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateSchedulePlaceBinding
    private var itemList = mutableListOf<WishRecentRecyclerItem>()
    lateinit var recyclerAdapter: WishRecentRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateSchedulePlaceBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.finishBtn.setOnClickListener{
            requireActivity().finish()
        }
        initRecycler()
    }
    private fun initRecycler(){
        recyclerAdapter = WishRecentRecyclerAdapter(itemList)
        binding.minusview.apply {
            adapter = recyclerAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(13))
            addItemDecoration(HorizontalItemDecorator(13))
        }
        itemList.apply {
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
            add(WishRecentRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size))
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
            add(WishRecentRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size))
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
            add(WishRecentRecyclerItem("안녕하신가요?안 안녕하시낙?", R.drawable.box_size))
            add(WishRecentRecyclerItem("맛집이에유~", R.drawable.box_size))
            add(WishRecentRecyclerItem("여기저기요기조기", R.drawable.box_size))
        }
    }
}