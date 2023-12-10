package com.dodoojuice.jforme.main.tapfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentMainCafeBinding
import com.dodoojuice.jforme.databinding.FragmentMainSeasonBinding
import com.dodoojuice.jforme.main.KeywordApi
import com.dodoojuice.jforme.main.MainCateListAdapter
import com.dodoojuice.jforme.main.MainFragment
import com.dodoojuice.jforme.main.MainGpsViewModel
import com.dodoojuice.jforme.main.MainRecyclerItem
import com.dodoojuice.jforme.main.MainRecyclerItemList
import com.dodoojuice.jforme.main.ResultSearchKeyword
import com.dodoojuice.jforme.main.VerticalItemDecorator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainSeasonFragment : Fragment() {
    private var _binding: FragmentMainSeasonBinding? = null
    private val binding get() = _binding!!
    lateinit var mainCateListAdapter: MainCateListAdapter
    private var cateItems = mutableListOf<MainRecyclerItemList>() //카테고리별 아이템 분류, 저장
    private val gpsViewModel : MainGpsViewModel by viewModels({ requireParentFragment() })
    private val BASE_URL = "https://dapi.kakao.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainSeasonBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        //searchKeyword("중앙역", 1, 0)
    }

    private fun initRecycler(){
        setItemList()
        mainCateListAdapter = MainCateListAdapter(requireActivity(), cateItems, gpsViewModel)
        binding.RecyclerList.apply {
            adapter = mainCateListAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(VerticalItemDecorator(10))
        }
        mainCateListAdapter.setOnItemClickListener(object : MainCateListAdapter.OnClickInterface{
            //내위치 버튼 클릭 시 현재 위치 찾아서 업데이트
            override fun onGpsClick() {
                (parentFragment as MainFragment).permissionResultLauncher.launch((parentFragment as MainFragment).permissions)
                (parentFragment as MainFragment).requestLocationUpdate()
            }
            override fun onChangeLocationClick() {
                TODO("Not yet implemented")

            }
        })

    }


    /* private fun addItemsAndMarkers(searchResult: ResultSearchKeyword?) {
         if (!searchResult?.documents.isNullOrEmpty()) {
             // 검색 결과 있음
             var items = mutableListOf<MainRecyclerItem>() //카테고리별 장소 데이터 임시 저장


             listItems.clear()                   // 리스트 초기화
             for (document in searchResult!!.documents) {
                 items.apply {
                     add(MainRecyclerItem(document.place_name, document., false ))
                 }


                 // 결과를 리사이클러 뷰에 추가
                 val item = ListLayout(document.place_name,
                     document.road_address_name,
                     document.address_name,
                     document.x.toDouble(),
                     document.y.toDouble())
                 listItems.add(item)

                 // 지도에 마커 추가
                 val point = MapPOIItem()
                 point.apply {
                     itemName = document.place_name
                     mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(),
                         document.x.toDouble())
                     markerType = MapPOIItem.MarkerType.BluePin
                     selectedMarkerType = MapPOIItem.MarkerType.RedPin
                 }
                 binding.mapView.addPOIItem(point)
             }
             listAdapter.notifyDataSetChanged()

             binding.btnNextPage.isEnabled = !searchResult.meta.is_end // 페이지가 더 있을 경우 다음 버튼 활성화
             binding.btnPrevPage.isEnabled = pageNumber != 1             // 1페이지가 아닐 경우 이전 버튼 활성화

         } else {
             // 검색 결과 없음
             Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
         }
     }*/


    //카테고리 분류
    private fun setItemList(){

        var categories = mutableListOf<String>()
        //카테고리 리스트 불러오기(내주변, jme의 제안, 축제, 팝업 등등...)
        categories.apply {
            add("내 주변")
            add("제이미의 제안")
            add("cate a")
            add("cate b")
            add("cate c")
        }
        for(i in 0 until categories.size){
            //이중 반복문으로 카테고리별 장소 불러온 만큼 add
            var items = mutableListOf<MainRecyclerItem>() //카테고리별 장소 데이터 임시 저장
            items.apply {
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, false ))
                add(MainRecyclerItem(categories[i], R.drawable.box_size, true ))
            }
            if(i < 2){
                cateItems.add(MainRecyclerItemList(categories[i], 1, items)) //내 주변, 제이미의 제안
            }
            else cateItems.add(MainRecyclerItemList(categories[i], 2, items)) //기타 카테고리별 장소 리스트 추가
            //items.clear()
        }
    }
    fun searchKeyword(keyword: String, page: Int, type : Int) {

        val retrofit = Retrofit.Builder()          // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KeywordApi::class.java)            // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword("KakaoAK 523fb37a69bc4d09d038442dc986c7e4", keyword, page)    // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                // 통신 성공
                if(!response.body()?.documents.isNullOrEmpty()){
                    Log.d("제발 살려줘세오ㅛ", response.body()!!.documents.toString())
                    //type = 0이면 위치 위치 [내주변]의 위치 변경
                    if(type == 0) getLocation(response.body()!!)
                    //type == 1이면 [내주변]의 아이템 설정
                    //else if (type == 1)
                }
                else Log.d("제발 살려", "ㅇㅇ아아")
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: ${t.message}")
            }
        })
    }
    fun getLocation(result : ResultSearchKeyword){

    }
}