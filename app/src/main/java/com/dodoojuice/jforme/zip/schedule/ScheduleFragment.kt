package com.dodoojuice.jforme.zip.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentScheduleBinding
import com.dodoojuice.jforme.login.join.JoinActivity


class ScheduleFragment(val position : Int) : Fragment() {
    lateinit var binding : FragmentScheduleBinding
    private var isImageClicked = false
    lateinit var viewPager2Adapter : ScheduleFragmentViewPagerAdapter
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var scheduleSummaryItem = mutableListOf<ScheduleSummaryItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.scheduleViewModel = scheduleViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScheduleData()
        initViewPager()
        binding.modify.setOnClickListener{
            val intent = Intent(requireContext(), ScheduleEditActivity::class.java)
            //intent.putExtra("item", scheduleViewModel.scheduleItem.value!!)
            startActivity(intent)
        }
    }

    private fun initScheduleData(){
        binding.scheduleTitle.text = "물놀이 하러 강릉 가유~"
        setItemList()
        scheduleViewModel.initScheduleData(scheduleSummaryItem, position, position)
    }
    private fun setItemList(){
        //장소 데이터 저장
        var places = mutableListOf<ScheduleSummaryPlaceItem>()
        //카테고리 리스트 불러오기(내주변, jme의 제안, 축제, 팝업 등등...)
        places.apply {
            add(ScheduleSummaryPlaceItem("chocolate coffee", position.toString(), "카페", "경기도 안산시 상록구", true, R.drawable.j, 37.479928, 126.900169))
            add(ScheduleSummaryPlaceItem("안산문화광장", position.toString(), "광장", "경기도 안산시 상록구", true, R.drawable.j, 37.480624,126.900735))
            add(ScheduleSummaryPlaceItem("한양대학교 에리카캠퍼스", "1.5", "대학교", "경기도 안산시 상록구", false, R.drawable.j, 37.481667,126.900713))
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
        }
    }

    private fun initViewPager(){
        viewPager2Adapter = ScheduleFragmentViewPagerAdapter(this@ScheduleFragment)
        viewPager2Adapter.addFragment(ScheduleSummaryFragment())
        viewPager2Adapter.addFragment(ScheduleMapFragment())

        binding.scheduleViewPager.apply {
            adapter = viewPager2Adapter
            isUserInputEnabled = false
        }

        //map, summary fragment 전환
        binding.mapjcon.setOnClickListener {
            if (isImageClicked){
                binding.mapjcon.setBackgroundResource(R.drawable.mapjcon)
                binding.scheduleViewPager.setCurrentItem(0, true)
            } else {
                binding.mapjcon.setBackgroundResource(R.drawable.list)
                binding.scheduleViewPager.setCurrentItem(1, true)
            }
            isImageClicked = !isImageClicked
        }
    }
    class ScheduleFragmentViewPagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment){
        private var fragments : ArrayList<Fragment> = ArrayList()

        override fun getItemCount(): Int {
            return if(fragments.size > 2) 2
            else fragments.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
        fun addFragment(fragment: Fragment){
            if(fragments.size <2) {
                fragments.add(fragment)
                notifyItemInserted(fragments.size - 1)
            }
        }
    }
}

class ScheduleViewModel : ViewModel(){
    //장소 및 교통수단 item
    private val _scheduleItem = MutableLiveData<MutableList<ScheduleSummaryItem>>()
    val scheduleItem: LiveData<MutableList<ScheduleSummaryItem>>
        get() = _scheduleItem

    //인원 수
    private val _schedulePersonnel = MutableLiveData<Int>()
    val schedulePersonnel : LiveData<Int>
        get() =_schedulePersonnel

    //비용
    private val _scheduleExpense = MutableLiveData<Int>()
    val scheduleExpense : LiveData<Int>
        get() = _scheduleExpense

    init {
        //_currentSize.value = 1
    }
    fun initScheduleData(itemList: MutableList<ScheduleSummaryItem>, Expense: Int, personnel: Int){
        _scheduleItem.value = itemList
        _scheduleExpense.value = Expense
        _schedulePersonnel.value = personnel
    }
    fun updateScheduleItem(itemList : MutableList<ScheduleSummaryItem>){
        _scheduleItem.value = itemList
    }
    fun minusExpense(Expense : Int){
        _scheduleExpense.value = _scheduleExpense.value?.minus(Expense)
    }
    fun plusExpense(Expense: Int){
        _scheduleExpense.value = _scheduleExpense.value?.plus(Expense)
    }
    fun updatePersonnel(personnel : Int){
        _schedulePersonnel.value = personnel
    }
}