package com.dodoojuice.jforme.place

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.database.RetrofitInstance.apiService
import com.dodoojuice.jforme.database.apidata
import com.dodoojuice.jforme.databinding.ActivityPlaceBinding
import com.dodoojuice.jforme.databinding.RecyclerPlacePriceItemBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class PlacePriceItem(var menu : String, var price : String)
class PlaceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlaceBinding
    private var priceItemList = mutableListOf<PlacePriceItem>()
    lateinit var placePriceRecyclerAdapter: PlaceActivityRecyclerAdapter
    private lateinit var viewPagerAdapter : PlaceViewPagerAdapter

    val category = 1
    val title = "투썸플레이스"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchApiData(13312306)
        initViewPager2()
    }
    private fun setBinding(response: apidata?){
        if(response != null) {
            binding.placeimg.setImageResource(R.drawable.picture) //장소 이미지
            binding.placeimg.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.grade.text = "3.4" //평점(5점 만점)
            binding.number.text = "45" //평점 남긴 인원 수
            binding.placetext.text = response.stores //장소 이름
            //장소 카테고리
            if (1 == 1) binding.wishCheck.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.jpc) //찜 했으면 분홍, 안 했으면 회색 하트
            binding.placeaddresstext.text = response.road_address//주소
            binding.kakaomap.setOnClickListener {
                //url로 카카오맵 연동
                val kakaoMapUrl = response.place_url
                val webpage: Uri = Uri.parse(kakaoMapUrl)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=net.daum.android.map")
                    )
                )
            }
            binding.noticetext.text =""

            //가격 정보 set
            priceItemList.apply {
                add(PlacePriceItem("아이스 아메리카노", "4500"))
                add(PlacePriceItem("카페 라떼", "5000"))
                add(PlacePriceItem("아이스 캬라멜 마끼아또", "5500"))
            }
            placePriceRecyclerAdapter = PlaceActivityRecyclerAdapter(priceItemList)
            binding.priceView.apply {
                adapter = placePriceRecyclerAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        else Toast.makeText(this, "값을 불러오지 못했습니다. 네트워크 연결을 확인하세요.", Toast.LENGTH_LONG).show()
    }
    private fun fetchApiData(id: Int) {
        val call: Call<apidata> = apiService.getApiData(id)
        call.enqueue(object : Callback<apidata> {
            override fun onResponse(call: Call<apidata>, response: Response<apidata>) {
                if (response.isSuccessful) {
                    // Successful response
                    val apiData = response.body()
                    Log.d("ApiDataResponse", "Response: $apiData")
                    // Process the API data response
                    Log.d("wwwwwwwwwww", response.body()?.stores.toString())
                    setBinding(response.body())
                    // Add a log statement to display the API data
                    Log.d("ApiDataResponse", "ApiData: $apiData")
                } else {
                    // Handle API error
                    Log.e("ApiDataResponse", "Error: ${response.code()}")
                    // Display an error message to the user
                }
            }

            override fun onFailure(call: Call<apidata>, t: Throwable) {
                // Handle network or other errors
                Log.e("ApiDataResponse", "Error: ${t.message}")
                // Display an error message to the user
            }
        })
    }

    private fun initViewPager2(){
        viewPagerAdapter = PlaceViewPagerAdapter(this@PlaceActivity)
        viewPagerAdapter.addFragment(PlaceReviewFragment())
        viewPagerAdapter.addFragment(PlaceCommunityFragment())
        viewPagerAdapter.addFragment(PlaceNearbyFragment())

        binding.viewPagerPlaceReview.apply {
            adapter = viewPagerAdapter
        }
        TabLayoutMediator(binding.tablayout, binding.viewPagerPlaceReview){
                tab, position ->
            when(position){
                0 -> tab.text = "리뷰"
                1 -> tab.text = "기록"
                2 -> tab.text = "주변"
            }
        }.attach()

    }
    class PlaceViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        var fragments : ArrayList<Fragment> = ArrayList()

        override fun getItemCount(): Int {
            return fragments.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
        fun addFragment(fragment: Fragment){
            fragments.add(fragment)
            notifyItemInserted(fragments.size-1)
        }
    }
}

class PlaceActivityRecyclerAdapter(var itemlist: MutableList<PlacePriceItem>): RecyclerView.Adapter<PlaceActivityRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerPlacePriceItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PlacePriceItem) {
            binding.menuname.text = data.menu
            binding.menuprice.text = "${data.price}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerPlacePriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}