package com.dodoojuice.jforme.place

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivityPlaceReviewWriteBinding
import com.dodoojuice.jforme.databinding.RecyclerMypageReviewItemBinding
import com.dodoojuice.jforme.databinding.RecyclerPlaceReviewItemBinding
import com.dodoojuice.jforme.databinding.RecyclerPlaceReviewWriteListBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainItemListAdapter
import com.dodoojuice.jforme.mypage.MypageReviewSubRecyclerAdapter

data class PlaceReviewWriteHashtag(var category : String, var itemList : ArrayList<String>)
class PlaceReviewWriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityPlaceReviewWriteBinding
    private val PERMISSION_CODE = 1000
    private var uri1 : Uri ?= null
    private var uri2 : Uri ?= null
    private var uri3 : Uri ?= null
    lateinit var reviewPreviewGoodRecyclerAdapter : ReviewWriteListRecyclerAdapter
    lateinit var reviewPreviewBadRecyclerAdapter : ReviewWriteListRecyclerAdapter
    private var goodItemList = mutableListOf<PlaceReviewWriteHashtag>()
    private var badItemList = mutableListOf<PlaceReviewWriteHashtag>()

    private lateinit var title : String
    private var cate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceReviewWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("title").toString()
        cate = intent.getIntExtra("cate", 0)
        initBinding()
    }

    private fun initBinding(){
        binding.editReview.setOnTouchListener(View.OnTouchListener { v, event ->
            if (v.id == R.id.editReview) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        })
        initRecycler()
        //별점
        binding.placename.text = title
        binding.ratingbar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener{
                    ratingBar, rating, fromUser ->
                binding.ratingText.text = "$rating"
            }

        //등록하기 버튼
        binding.finishBtn.setOnClickListener{
            //해시태그 체크된 것 가져오기(goot, bad 합쳐서)
            val selectedHashtagList = reviewPreviewGoodRecyclerAdapter.getItemSelected()
            selectedHashtagList.addAll(reviewPreviewBadRecyclerAdapter.getItemSelected())
            Toast.makeText(this, "${selectedHashtagList.toString()}", Toast.LENGTH_LONG).show()
            binding.ratingText.text //별점
            //finish()
        }
        binding.photoView1.setOnClickListener {
            if(uri1!=null && uri2!=null && uri3 !=null) uri1 = null
            checkPermission()
        }
        binding.photoView2.setOnClickListener {
            if(uri1!=null && uri2!=null && uri3 !=null) uri2 = null
            checkPermission()
        }
        binding.photoView3.setOnClickListener {
            if(uri1!=null && uri2!=null && uri3 !=null) uri3 = null
            checkPermission()
        }
    }
    private fun initRecycler(){
        when(cate){
            1 -> {
                goodItemList.add(ReviewHashtagList().menuList_good)
                badItemList.add(ReviewHashtagList().menuList_bad)
            }
        }
        goodItemList.add(ReviewHashtagList().moodList_good)
        goodItemList.add(ReviewHashtagList().convenienceList_good)
        goodItemList.add(ReviewHashtagList().etcList_good)
        badItemList.add(ReviewHashtagList().moodList_bad)
        badItemList.add(ReviewHashtagList().convenienceList_bad)
        badItemList.add(ReviewHashtagList().etcList_bad)

        reviewPreviewGoodRecyclerAdapter = ReviewWriteListRecyclerAdapter(goodItemList)
        binding.reviewHashtagGoodRecyclerView.apply {
            adapter = reviewPreviewGoodRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(HorizontalItemDecorator(10))
        }
        reviewPreviewBadRecyclerAdapter = ReviewWriteListRecyclerAdapter(badItemList)
        binding.reviewHashtagBadRecyclerView.apply {
            adapter = reviewPreviewBadRecyclerAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(HorizontalItemDecorator(10))
        }
    }
    private fun checkPermission(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                //스토리지 읽기 권한이 허용이면 커스텀 앨범 띄워주기
                //갤러리 호출
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                activityResult.launch(intent)
                //권한 있을 경우 : PERMISSION_GRANTED
                //권한 없을 경우 : PERMISSION_DENIED
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                //권한을 명시적으로 거부한 경우 : ture
                //다시 묻지 않음을 선택한 경우 : false
                //다이얼로그를 띄워 권한 팝업을 해야하는 이유 및 권한팝업을 허용하여야 접근 가능하다는 사실을 알려줌
                showPermissionAlertDialog()
            }
            else -> {
                //권한 요청
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    PERMISSION_CODE
                )
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허용클릭
                    //앨범으로 이동시키기
                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES)) {
                    //권한 처음으로 거절 했을 경우
                    //한번더 권한 요청
                    showPermissionAlertDialog()
                } else {
                    //권한 두번째로 거절 한 경우 (다시 묻지 않음)
                    //설정 -> 권한으로 이동하는 다이얼로그
                    goSettingActivityAlertDialog()
                }
            }
        }
    }

    //갤러리 호출 결과 가져오기 -> db에 어떤 형식으로 저장하는지 모름.
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        //결과 코드 ok, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            //값 담기
            if(uri1 == null){
                uri1 = it.data!!.data!!
                val imgView = binding.photoView1
                //화면에 보여주기
                Glide.with(this)
                    .load(uri1)//이미지
                    .into(imgView) //보여줄 위치
            }
            else if (uri2 == null) {
                uri2 = it.data!!.data!!
                val imgView = binding.photoView2
                //화면에 보여주기
                Glide.with(this)
                    .load(uri2)//이미지
                    .into(imgView) //보여줄 위치
            }
            else if(uri3 == null){
                uri3 = it.data!!.data!!
                val imgView = binding.photoView3
                //화면에 보여주기
                Glide.with(this)
                    .load(uri3)//이미지
                    .into(imgView) //보여줄 위치
            }
        }
    }
    private fun showPermissionAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("사진을 선택 하시려면 권한이 필요합니다.")
            .setPositiveButton("허용하기") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    PERMISSION_CODE
                )
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }
    private fun goSettingActivityAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("앨범에 접근 하기 위한 권한이 필요합니다.\n권한 -> 사진 및 동영상 -> 허용")
            .setPositiveButton("허용하러 가기") { _, _ ->
                val goSettingPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                goSettingPermission.data = Uri.parse("package:com.dodoojuice.jforme")
                startActivity(goSettingPermission)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }
}

class ReviewWriteListRecyclerAdapter(var cateList: MutableList<PlaceReviewWriteHashtag>): RecyclerView.Adapter<ReviewWriteListRecyclerAdapter.Holder>() {
    private var reviewWriteItemRecyclerAdapterList = mutableListOf<ReviewWriteItemRecyclerAdapter>()

    inner class Holder(private val binding: RecyclerPlaceReviewWriteListBinding): RecyclerView.ViewHolder(binding.root) {

        private val reviewWriteItemRecyclerAdapter = ReviewWriteItemRecyclerAdapter()
        fun bind(list: PlaceReviewWriteHashtag, position: Int) {
            binding.title.text = list.category
            binding.reviewHashtagRecyclerView.apply {
                adapter = reviewWriteItemRecyclerAdapter
                layoutManager = LinearLayoutManager(binding.reviewHashtagRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                addItemDecoration(HorizontalItemDecorator(10))
            }
            reviewWriteItemRecyclerAdapter.submitList(list.itemList)
            if(reviewWriteItemRecyclerAdapterList.size <= position)
                reviewWriteItemRecyclerAdapterList.add(reviewWriteItemRecyclerAdapter)
        }
    }
    fun getItemSelected(): MutableList<String> {
        var selectedItemList = mutableListOf<String>()
        for (adapter in reviewWriteItemRecyclerAdapterList){
            for (item in adapter.getSelectedItem()) {
                if(item != "") selectedItemList.add(item)
            }
        }
        return selectedItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerPlaceReviewWriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cateList[position], position)
    }

    override fun getItemCount(): Int {
        return cateList.size
    }
}
class ReviewWriteItemRecyclerAdapter(): ListAdapter<String, ReviewWriteItemRecyclerAdapter.Holder>(DiffUtils()) {
    private var selectedItem = arrayListOf<String>()

    inner class Holder(private val binding: RecyclerMypageReviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.text.text = data
            binding.root.setOnClickListener{
                //선택하면 색 바뀜
                applySelection(binding, data)
                onItemClickListener?.let{it(data)}
            }
        }
    }
    private fun applySelection(binding: RecyclerMypageReviewItemBinding, text: String) {
        if (selectedItem.contains(text)) {
            selectedItem.remove(text)
            binding.text.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.wg))
        } else {
            selectedItem.add(text)
            binding.text.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.secondColor))
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerMypageReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtils : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    fun getSelectedItem() = selectedItem

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
}

class ReviewHashtagList{
    //음식점 1, 나머지 0

    val menuList_good = PlaceReviewWriteHashtag("메뉴", arrayListOf<String>("다양한 종류", "많은 양", "신선함", "특별한 메뉴", "가성비", "다양한 술", "음료 맛집", "커피 맛집", "디저트 맛집", "현지의 맛", "잡내 없음", "맞춤 조리"))
    val moodList_good = PlaceReviewWriteHashtag("분위기", arrayListOf<String>("독특한 컨셉", "단체 모임", "선곡이 트렌디", "아늑함", "넓은 매장", "뷰가 좋음", "사진 명소", "대화하기 좋음"))
    val convenienceList_good = PlaceReviewWriteHashtag("편의", arrayListOf<String>("화장실 청결", "주차 편리", "좌석 편안", "반려동물 가능", "아이 동반", "매장 청결"))
    val etcList_good = PlaceReviewWriteHashtag("기타", arrayListOf<String>("비싼 값 함", "저렴", "이색 체험", "콘센트 있음", "개별 룸", "빠른 서비스", "친절", "부대시설 좋음", "쾌적한 대기공간"))

    val menuList_bad = PlaceReviewWriteHashtag("메뉴",arrayListOf<String>("적은 양", "간이 셈", "간이 약함", "특별함 없음", "적은 메뉴 수", "음료 별로", "커피 별로", "디저트 별로", "잡내 심함"))
    val moodList_bad = PlaceReviewWriteHashtag("분위기",arrayListOf<String>("협소한 공간", "불쾌한 음악", "뷰가 별로", "소란스러움"))
    val convenienceList_bad = PlaceReviewWriteHashtag("편의",arrayListOf<String>("화장실 불쾌", "주차 협소", "불편한 좌석", "매장 관리 미흡"))
    val etcList_bad = PlaceReviewWriteHashtag("기타",arrayListOf<String>("높은 가격대", "느린 서비스", "불친절", "대기 있음", "협소한 대기공간"))
}