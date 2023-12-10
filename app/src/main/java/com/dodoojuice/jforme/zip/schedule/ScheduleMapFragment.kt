package com.dodoojuice.jforme.zip.schedule

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentScheduleMapBinding
import com.dodoojuice.jforme.databinding.RecyclerZipScheduleMapItemBinding
import com.dodoojuice.jforme.wish.WishRecentRecyclerItem
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
//data class ScheduleMapRecyclerItem(var title : String, var img : Int, var address: String, var lat : Float, var lng : Float)

class ScheduleMapFragment : Fragment() {
    lateinit var binding : FragmentScheduleMapBinding
    private lateinit var mapView : MapView
    private lateinit var scheduleMapRecyclerAdapter: ScheduleMapRecyclerAdapter
    private var scheduleMapItemList = mutableListOf<ScheduleSummaryItem>()
    private var mapPolyline = MapPolyline()
    private val scheduleViewModel: ScheduleViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_map, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onResume() {
        super.onResume()
        initMapRecycler()
        initMapView()
    }

    private fun initMapRecycler(){
        initItemList()
        scheduleMapRecyclerAdapter = ScheduleMapRecyclerAdapter(scheduleMapItemList)
        binding.scheduleMapRecyclerView.apply {
            adapter = scheduleMapRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            //addItemDecoration(HorizontalItemDecorator(10))
        }
    }
    private fun initItemList(){
        for(i in 0 until scheduleViewModel.scheduleItem.value!!.size step(2)){
            val copyItem = scheduleViewModel.scheduleItem.value!![i].copy()
            scheduleMapItemList.add(copyItem)
        }
    }

    private fun initMapView(){
        mapView = MapView(this.context)
        binding.mapView.addView(mapView)

        //장소별 마커 찍고 선으로 잇기
        mapPolyline.lineColor = Color.argb(128, 50, 0, 255)
        for(i in 0 until scheduleMapItemList.size){
            var mapPoint = MapPoint.mapPointWithGeoCoord(scheduleMapItemList[i].placeItem.lat, scheduleMapItemList[i].placeItem.lng)
            var marker = setMarker(scheduleMapItemList[i].placeItem.title, mapPoint)
            mapView.addPOIItem(marker)
            mapPolyline.addPoint(mapPoint)
        }
        mapView.addPolyline(mapPolyline)

        //마커 종합 위치에 따라 카메라 조절
        var mapPointBounds = MapPointBounds(mapPolyline.mapPoints)
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, 100))


        /*//위도, 경도 설정
        var mapPoint = MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)
        //마커 생성
        var marker = setMarker("a", mapPoint)
        mapView.addPOIItem(marker)
        mapView.setMapCenterPoint(mapPoint, true)*/
    }
    private fun setMarker(mName:String, mPoint : MapPoint): MapPOIItem {
        val marker = MapPOIItem()
        marker.apply{
            itemName = mName
            mapPoint = mPoint
            //markerType = MapPOIItem.MarkerType.BluePin
            //customImageResourceId = R.drawable.ic_launcher_background //마커 이미지(비트맵)
            markerType = MapPOIItem.MarkerType.BluePin //지우고 위 코드 사용하면 됨
            selectedMarkerType = MapPOIItem.MarkerType.RedPin //클릭시 마커 이미지

            setCustomImageAnchor(0.5f, 1.0f)
            isCustomImageAutoscale = false
        }
        return marker
    }
    /*class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.balloon_layout, null)
        val name: TextView = mCalloutBalloon.findViewById(R.id.ball_tv_name)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            name.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            return mCalloutBalloon
        }
    }*/
    override fun onPause() {
        super.onPause()
        binding.mapView.removeAllViews()
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.removeAllPolylines()
        mapView.removeAllPOIItems()
    }
}

class ScheduleMapRecyclerAdapter(var itemlist: MutableList<ScheduleSummaryItem>): RecyclerView.Adapter<ScheduleMapRecyclerAdapter.Holder>() {

    inner class Holder(private val binding: RecyclerZipScheduleMapItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            //리스트 아이템 클릭시
            binding.root.setOnClickListener{
                //Toast.makeText(binding.root.context, "item = ${binding.place.text}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(placelist: ScheduleSummaryItem) {
            val placeItem = placelist.placeItem
            binding.placename.text = placeItem.title
            binding.mapImage.setImageResource(placeItem.placeImg)
            binding.scheduleMapAddress.text = placeItem.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerZipScheduleMapItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemlist[position])
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}