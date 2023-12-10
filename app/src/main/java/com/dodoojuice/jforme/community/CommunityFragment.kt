package com.dodoojuice.jforme.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCommunityBinding
import com.dodoojuice.jforme.databinding.RecyclerCommunityItemBinding
import com.dodoojuice.jforme.main.VerticalItemDecorator


data class CommunityRecyclerItem(var pImg: Int, var nickname: String, var title: String, var photo:MutableList<Int>, var likeIsChecked : Boolean, var likeCount : Int, var commentCount : Int, var text : MutableList<String>)
                                    //프로필 사진, 닉네임, 글 제목, 사진(리스트로 바꿔야 할 듯), 사진 수?리스트면 필요없나?, 따봉 눌렀는지, 글의 따봉 수, 댓글 수, 본문 내용)
class CommunityFragment : Fragment() {

    lateinit var binding: FragmentCommunityBinding
    lateinit var recyclerAdapter: CommunityRecyclerAdapter
    private var itemList = mutableListOf<CommunityRecyclerItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        initRecycler()
        binding.wrtieButton.setOnClickListener{
            //글쓰기 Activity
            val intent = Intent(context, CommunityWriteActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun initRecycler() {
        recyclerAdapter = CommunityRecyclerAdapter(itemList, this)
        binding.recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        //이미지 리스트 설정
        val imgList = mutableListOf<Int>(R.drawable.pinkjheart, R.drawable.box_size, R.drawable.bell)
        //text 리스트 설정
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

class CommunityRecyclerAdapter(var itemlist: MutableList<CommunityRecyclerItem>, fragment: Fragment): RecyclerView.Adapter<CommunityRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerCommunityItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            /*//리스트 아이템 클릭시
            binding.root.setOnClickListener{
                Toast.makeText(binding.root.context, "item = ${binding.title.text}", Toast.LENGTH_SHORT).show()
            }*/
        }
        fun bind(data: CommunityRecyclerItem) {
            binding.option.setOnClickListener{Toast.makeText(binding.root.context, "신고하기 버튼 추가", Toast.LENGTH_SHORT).show()}
            binding.profileImg.setImageResource(data.pImg)
            binding.nickname.text = data.nickname
            binding.title.text = data.title
            if (data.likeIsChecked) binding.likeImg.setBackgroundResource(R.drawable.pinkjheart)
            binding.likeCount.text = data.likeCount.toString()
            binding.commentCount.text = data.commentCount.toString()
            binding.text.text = data.text[0]

            var recyclerAdapter = ImageSliderAdapter(binding.root.context, data.photo)
            binding.photoView.apply {
                adapter = recyclerAdapter
                offscreenPageLimit = 1
                registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        setCurrentIndicator(position)
                        binding.text.text = data.text[position]
                    }
                })
            }
            setupIndicators(data.photo.size)
        }
        private fun setupIndicators(count: Int) {
            val indicators = arrayOfNulls<ImageView>(count)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 8, 16, 8)
            for (i in indicators.indices) {
                indicators[i] = ImageView(binding.root.context)
                indicators[i]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.bg_indicator_inactive
                    )
                )
                indicators[i]!!.layoutParams = params
                binding.layoutIndicators.addView(indicators[i])
            }
            setCurrentIndicator(0)
        }
        private fun setCurrentIndicator(position: Int) {
            val childCount: Int = binding.layoutIndicators.childCount
            for (i in 0 until childCount) {
                val imageView = binding.layoutIndicators.getChildAt(i) as ImageView
                if (i == position) {
                    imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_indicator_active
                        )
                    )
                } else {
                    imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.bg_indicator_inactive
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerCommunityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}

class ImageSliderAdapter(private val context: Context, private val sliderImage: MutableList<Int>) :
    RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_community_image, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSliderImage(sliderImage[position])
    }

    override fun getItemCount(): Int {
        return sliderImage.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView

        init {
            mImageView = itemView.findViewById<ImageView>(R.id.imageSlider)
        }

        fun bindSliderImage(image : Int) {
            mImageView.setImageResource(image)
        }
    }
}