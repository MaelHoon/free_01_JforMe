package com.dodoojuice.jforme.zip.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dodoojuice.jforme.MainActivity
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentScheduleSummaryBinding
import com.dodoojuice.jforme.databinding.RecyclerZipScheduleSummaryPlaceBinding
import com.dodoojuice.jforme.databinding.RecyclerZipScheduleSummaryTransportationBinding
import com.dodoojuice.jforme.main.MainCateListAdapter
import com.dodoojuice.jforme.main.VerticalItemDecorator
import com.dodoojuice.jforme.place.PlaceActivity
import java.io.Serializable

data class ScheduleSummaryPlaceItem(var title : String, var stayTime : String, var cate : String, var address : String, var checkWish : Boolean, var placeImg : Int, var lat : Double, var lng : Double): Serializable
data class ScheduleSummaryTransportItem(var transport : String, var moveTime : String, var distance : String): Serializable
data class ScheduleSummaryItem(var type : Int, var placeItem : ScheduleSummaryPlaceItem, var transportItem : ScheduleSummaryTransportItem) : Serializable
                                //타입            장소 이름               체류 시간               카테고리            주소             //         이동 수단               이동 시간                   거리

class ScheduleSummaryFragment : Fragment() {
    lateinit var binding : FragmentScheduleSummaryBinding
    lateinit var scheduleSummaryAdapter: ScheduleSummaryAdapter
    private var scheduleSummaryItem = mutableListOf<ScheduleSummaryItem>()
    private val scheduleViewModel: ScheduleViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_summary, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("summaryfragmentOndestroy", scheduleViewModel.scheduleItem.value.toString())
        //scheduleViewModel.invalidate()
    }

    override fun onResume() {
        super.onResume()

        Log.d("summaryfragment", scheduleSummaryItem.toString())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        Log.d("summaryFragmentOnViewCreated", scheduleSummaryItem.toString())
    }
    private fun initRecycler(){
        if(scheduleViewModel.scheduleItem.value != null)setItemList()
        scheduleSummaryAdapter = ScheduleSummaryAdapter(scheduleSummaryItem)
        binding.scheduleSummaryRecycler.apply {
            adapter = scheduleSummaryAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            //addItemDecoration(VerticalItemDecorator(10))
        }

    }
    private fun setItemList(){
        for(item in scheduleViewModel.scheduleItem.value!!){
            val copyItem = item.copy()
            scheduleSummaryItem.add(copyItem)
        }


        //장소 데이터 저장
        /*var places = mutableListOf<ScheduleSummaryPlaceItem>()
        //카테고리 리스트 불러오기(내주변, jme의 제안, 축제, 팝업 등등...)
        places.apply {
            add(ScheduleSummaryPlaceItem("chocolate coffee", "2", "카페", "경기도 안산시 상록구", true, R.drawable.j, 37.479928, 126.900169))
            add(ScheduleSummaryPlaceItem("안산문화광장", "1", "광장", "경기도 안산시 상록구", true, R.drawable.j, 37.480624,126.900735))
            add(ScheduleSummaryPlaceItem("한양대학교 에리카캠퍼스", "1.5", "대학교", "경기도 안산시 상록구", false, R.drawable.j, 37.481667,126.900713))
*//*            add(ScheduleSummaryPlaceItem("월드아파트", "4", "아파트", "경기도 안산시 상록구", false, R.drawable.j))
            add(ScheduleSummaryPlaceItem("다이소", "0.8", "잡화점", "경기도 안산시 상록구", true, R.drawable.j))
            add(ScheduleSummaryPlaceItem("chocolate coffee", "2", "카페", "경기도 안산시 상록구", true, R.drawable.j))
            add(ScheduleSummaryPlaceItem("chocolate coffee", "2", "카페", "경기도 안산시 상록구", false, R.drawable.j))*//*
        }
        var transportations = mutableListOf<ScheduleSummaryTransportItem>()
        transportations.apply {
            add(ScheduleSummaryTransportItem("버스", "1.3", "0.8"))
            add(ScheduleSummaryTransportItem("버스, 전철", "0.5", "0.8"))
            add(ScheduleSummaryTransportItem("버스, 택시", "1.3", "0.3"))
            add(ScheduleSummaryTransportItem("버스", "1.3", "0.8"))
            add(ScheduleSummaryTransportItem("버스", "1.3", "0.8"))
            add(ScheduleSummaryTransportItem("버스", "1.3", "0.8"))
        }
        var nullTransportation = ScheduleSummaryTransportItem("", "", "")
        var nullPlace = ScheduleSummaryPlaceItem("", "", "", "", false, 0, 0.0, 0.0)
        for (i in 0 until places.size){
            if(i < places.size - 1) {
                scheduleSummaryItem.add(ScheduleSummaryItem(1, places[i], nullTransportation))
                scheduleSummaryItem.add(ScheduleSummaryItem(2, nullPlace, transportations[i]))
            }
            else scheduleSummaryItem.add(ScheduleSummaryItem(1, places[i], nullTransportation))
        }*/
    }
}

class ScheduleSummaryAdapter(var itemlist : MutableList<ScheduleSummaryItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class SummaryPlaceViewHolder(private val binding : RecyclerZipScheduleSummaryPlaceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(placelist : ScheduleSummaryItem, pos : Int){
            val placeItem = placelist.placeItem
            binding.apply {
                if(placeItem.checkWish) wishBtn.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.jpc)
                palcename.text = placeItem.title
                palcename.setOnClickListener{
                    val intent = Intent(root.context, PlaceActivity::class.java)
                    ContextCompat.startActivity(root.context, intent, null)
                }
                photoView.setImageResource(placeItem.placeImg)
                photoView.setOnClickListener{
                    val intent = Intent(root.context, PlaceActivity::class.java)
                    ContextCompat.startActivity(root.context, intent, null)
                }
                time.text = "${placeItem.stayTime}시간 체류"
                category.text = placeItem.cate
                address.text = placeItem.address
                placePosition.text = (pos/2+1).toString()
            }
        }
    }
    inner class SummaryTransportationViewHolder(private val binding : RecyclerZipScheduleSummaryTransportationBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transportList : ScheduleSummaryItem){
            val transportItem = transportList.transportItem
            binding.apply {
                transportation.text = transportItem.transport
                moveTime.text = "${transportItem.distance} km (${transportItem.moveTime}분 소요 예상)"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> return SummaryPlaceViewHolder(RecyclerZipScheduleSummaryPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> return SummaryTransportationViewHolder(RecyclerZipScheduleSummaryTransportationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemlist[position].type
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(itemlist[position].type){
            1-> {
                (holder as ScheduleSummaryAdapter.SummaryPlaceViewHolder).bind(itemlist[position], position)
                //holder.setIsRecyclable(false)
            }
            else-> {
                (holder as ScheduleSummaryAdapter.SummaryTransportationViewHolder).bind(itemlist[position])
                //holder.setIsRecyclable(false)
            }
        }
    }

}
