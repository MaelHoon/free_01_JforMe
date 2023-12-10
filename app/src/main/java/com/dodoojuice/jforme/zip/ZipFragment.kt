package com.dodoojuice.jforme.zip

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.MainActivity
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentZipBinding
import com.dodoojuice.jforme.databinding.RecyclerMainItemBinding
import com.dodoojuice.jforme.databinding.RecyclerZipItemBinding
import com.dodoojuice.jforme.login.join.JoinActivity
import com.dodoojuice.jforme.main.MainCateListAdapter
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.MainRecyclerItemList
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.zip.create.CreateScheduleActivity
import com.dodoojuice.jforme.zip.schedule.ScheduleActivity

data class ZipRecyclerItem(var region: String, var date: String, var title: String)

class ZipFragment : Fragment() {
    lateinit var binding: FragmentZipBinding
    lateinit var recyclerAdapter: ZipRecyclerAdapter
    private var itemList = mutableListOf<ZipRecyclerItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZipBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }

    private fun initRecycler() {
        binding.makescheduletext.setOnClickListener{
            val intent = Intent(activity, CreateScheduleActivity::class.java)
            startActivity(intent)
        }
        recyclerAdapter = ZipRecyclerAdapter(itemList)
        binding.recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        //item 추가
        itemList.add(ZipRecyclerItem("강릉", "2023.01.01 ~ 2023.02.02", "신ㄴ영이가 자꾸 나를 귀찮게 굴허"))
        itemList.add(ZipRecyclerItem("서울", "2023.01.01 ~ 2023.02.02", "dkdkdkkdkdgmksgdklj"))
        itemList.add(ZipRecyclerItem("경기", "2023.01.01 ~ 2023.02.02", "두근두근 경기도 여행 계곡 가자"))
        itemList.add(ZipRecyclerItem("안산", "2023.01.01 ~ 2023.02.02", "course4"))
        itemList.add(ZipRecyclerItem("춘천", "2023.01.01 ~ 2023.02.02", "course5"))
        itemList.add(ZipRecyclerItem("거제", "2023.01.01 ~ 2023.02.02", "course6"))
        itemList.add(ZipRecyclerItem("제주", "2023.01.01 ~ 2023.02.02", "course7"))
    }
}
class ZipRecyclerAdapter(var itemlist: MutableList<ZipRecyclerItem>): RecyclerView.Adapter<ZipRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerZipItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, ScheduleActivity::class.java)
                ContextCompat.startActivity(binding.root.context, intent, null)
            }
        }
        fun bind(data: ZipRecyclerItem) {
            binding.regiontext.text = data.region
            binding.daytext.text = data.date
            binding.title.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerZipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}