package com.dodoojuice.jforme.place

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentPlaceReviewBinding
import com.dodoojuice.jforme.databinding.RecyclerPlaceReviewItemBinding
import com.dodoojuice.jforme.databinding.RecyclerZipItemBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.mypage.MypageReviewSubRecyclerAdapter
import com.dodoojuice.jforme.zip.ZipRecyclerItem
import com.dodoojuice.jforme.zip.schedule.ScheduleActivity

data class ReviewRecyclerItem(var imgList : ArrayList<Int>, var name : String, var profileImg : Int, var text : String)
class PlaceReviewFragment : Fragment() {
    lateinit var binding : FragmentPlaceReviewBinding
    private var reviewItemList = mutableListOf<ReviewRecyclerItem>()
    private var previewItemList = mutableListOf<String>()
    private lateinit var reviewItemRecyclerAdapter: PlaceReviewRecyclerAdapter
    lateinit var reviewPreviewRecyclerAdapter : MypageReviewSubRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        binding.writeReviewBtn.setOnClickListener{
            val intent = Intent(binding.root.context, PlaceReviewWriteActivity::class.java)
            intent.putExtra("cate", (activity as PlaceActivity).category)
            intent.putExtra("title", (activity as PlaceActivity).title)
            ContextCompat.startActivity(binding.root.context, intent, null)
        }
    }
    private fun initRecycler(){
        reviewPreviewRecyclerAdapter = MypageReviewSubRecyclerAdapter(previewItemList, 0)
        binding.hashtagRecycler.apply {
            adapter = reviewPreviewRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            addItemDecoration(HorizontalItemDecorator(10))
            setHasFixedSize(true)
        }
        previewItemList.apply {
            add("가성비가 좋아요")
            add("맛대가리가 없어요")
            add("청결해요")
            add("직원이 친절해요")
            add("선곡이 좋아요")
            add("그만")
            add("이건 나오면 안 돼")
        }

        reviewItemRecyclerAdapter = PlaceReviewRecyclerAdapter(reviewItemList)
        binding.reviewRecycler.apply {
            adapter = reviewItemRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        //이미지는 최대 3개, 리스트로 전달
        var imgList = arrayListOf<Int>(R.drawable.bell, R.drawable.ic_launcher_background, R.drawable.picture)
        var imgList2 = arrayListOf<Int>(R.drawable.img, R.drawable.pinkjheart, 0)
        var imgList3 = arrayListOf<Int>(0, 0, 0)
        var imgList4 = arrayListOf<Int>(0, 0, R.drawable.pinkjheart)
        reviewItemList.apply {
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.ic_launcher_background, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList2, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList3, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList4, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList2, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList3, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
            add(ReviewRecyclerItem(imgList, "쥬예용", R.drawable.j, "안녕하세요? 쥬예요~ 오늘 자장면을 먹었는데 오랜만에 옛날 짜장을 먹는 느낌이었어요. 요새 자장면이 맛이 되게 없는데 안산에서는 그나마 제일 맛있었던 거 같아요. 안산은 왜 자장면 맛집이 별로 없담??"))
        }
    }
}
class PlaceReviewRecyclerAdapter(var itemlist: MutableList<ReviewRecyclerItem>): RecyclerView.Adapter<PlaceReviewRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerPlaceReviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            /*binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, ScheduleActivity::class.java)
                ContextCompat.startActivity(binding.root.context, intent, null)
            }*/
        }
        fun bind(data: ReviewRecyclerItem) {
            if(data.imgList[0] != 0) {
                binding.reviewPhoto1.setImageResource(data.imgList[0])
                binding.reviewPhoto1.visibility = View.VISIBLE
                if(data.imgList[1] != 0) {
                    binding.reviewPhoto2.setImageResource(data.imgList[1])
                    binding.reviewPhoto2.visibility = View.VISIBLE
                    if(data.imgList[2] != 0) {
                        binding.reviewPhoto3.setImageResource(data.imgList[2])
                        binding.reviewPhoto3.visibility = View.VISIBLE
                    }
                }
            }
            binding.nickname.text = data.name
            binding.profileimg.setImageResource(data.profileImg)
            binding.reviewText.text = data.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerPlaceReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}