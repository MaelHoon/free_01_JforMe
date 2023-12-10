package com.dodoojuice.jforme.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.MainActivity
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentMypageRecordBinding
import com.dodoojuice.jforme.databinding.FragmentMypageReviewBinding
import com.dodoojuice.jforme.databinding.RecyclerMainItemBinding
import com.dodoojuice.jforme.databinding.RecyclerMypageReviewItemBinding
import com.dodoojuice.jforme.databinding.RecyclerMypageReviewListBinding
import com.dodoojuice.jforme.databinding.RecyclerZipItemBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainCateListAdapter
import com.dodoojuice.jforme.main.MainItemListAdapter
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.MainRecyclerItemList
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.zip.ZipRecyclerItem

data class MypageReviewMainItem(var region : String, var name : String, var img : Int, var score : Double, var review: MutableList<String>)

class MypageReviewFragment : Fragment() {
    lateinit var binding: FragmentMypageReviewBinding
    lateinit var reviewRecyclerAdapter: MypageReviewMainRecyclerAdapter
    private var reviewItemList = mutableListOf<MypageReviewMainItem>() //main item별 리뷰 요약(sub) item 저장

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageReviewBinding.inflate(inflater, container, false)
        initRecycler()
        return binding.root
    }
    private fun initRecycler(){
        setItemList()
        reviewRecyclerAdapter = MypageReviewMainRecyclerAdapter(reviewItemList)
        binding.recycler.apply {
            adapter = reviewRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
        }
    }
    private fun setItemList(){
        //카테고리 리스트 불러오기(내주변, jme의 제안, 축제, 팝업 등등...)
        for(i in 1..10){
            //이중 반복문으로 아이템마다 리뷰요약 포함해서 add
            var reviews = mutableListOf<String>() //카테고리별 장소 데이터 임시 저장
            reviews.apply {
                add("가성아아아아아아ㅏ아아비")
                add("분위기 좋이이아어 음")
                add("매움ㄷㄷㄴㅇ")
                add("직원 불친절야야ㅑ야야야")
            }
            reviewItemList.add(MypageReviewMainItem("안산", "투써뮤ㅡ레이스", R.drawable.j, 5.0, reviews))
        }
    }
}
class MypageReviewMainRecyclerAdapter(var itemlist: MutableList<MypageReviewMainItem>): RecyclerView.Adapter<MypageReviewMainRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerMypageReviewListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                //Toast.makeText(binding.root.context, "item = ${binding.regiontext.text}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(data: MypageReviewMainItem) {
            binding.region.text = data.region
            binding.score.text = "%.1f / 5".format(data.score)
            binding.name.text = data.name
            binding.image.setImageResource(data.img)
            binding.reviews.apply {
                adapter = MypageReviewSubRecyclerAdapter(data.review, 1)
                layoutManager = GridLayoutManager(binding.reviews.context, 2)
                setHasFixedSize(true)
                //addItemDecoration(VerticalItemDecorator(2))
                //addItemDecoration(HorizontalItemDecorator(13))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMypageReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}

class MypageReviewSubRecyclerAdapter(var itemlist : MutableList<String>, var where : Int) : RecyclerView.Adapter<MypageReviewSubRecyclerAdapter.Holder>(){
    inner class Holder(private val binding: RecyclerMypageReviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                //Toast.makeText(binding.root.context, "item = ${binding.regiontext.text}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(data: String) {
            binding.text.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMypageReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        if (where == 1) {
            return if (itemlist.size > 4) 4
            else itemlist.size
        }
        else{
            return if (itemlist.size > 6) 6
            else itemlist.size
        }
    }
}