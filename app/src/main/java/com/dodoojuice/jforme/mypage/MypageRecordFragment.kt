package com.dodoojuice.jforme.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentMypageRecordBinding
import com.dodoojuice.jforme.databinding.RecyclerMypageRecordItemBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.VerticalItemDecorator

data class MypageRecordItem(var recordImage: Int)
//커뮤니티에 작성한 글 목록
class MypageRecordFragment : Fragment() {
    lateinit var binding : FragmentMypageRecordBinding
    lateinit var recordRecyclerAdapter: MypageRecordRecyclerAdapter
    private var recordItemList = mutableListOf<MypageRecordItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageRecordBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        recordRecyclerAdapter = MypageRecordRecyclerAdapter(recordItemList)
        binding.recordRecycler.apply {
            adapter = recordRecyclerAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(13))
            addItemDecoration(HorizontalItemDecorator(13))
        }
        //item 추가
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
        recordItemList.add(MypageRecordItem(R.drawable.ic_launcher_background))
    }
}
class MypageRecordRecyclerAdapter(var itemlist: MutableList<MypageRecordItem>): RecyclerView.Adapter<MypageRecordRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerMypageRecordItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                //Toast.makeText(binding.root.context, "item = ${binding.regiontext.text}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(data: MypageRecordItem) {
            binding.recordImg.setImageResource(data.recordImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMypageRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}