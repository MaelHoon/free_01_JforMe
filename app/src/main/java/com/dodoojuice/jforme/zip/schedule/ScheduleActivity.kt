package com.dodoojuice.jforme.zip.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivityScheduleBinding
import com.dodoojuice.jforme.databinding.RecyclerZipScheduleDayBinding
import com.dodoojuice.jforme.main.HorizontalItemDecorator
import com.dodoojuice.jforme.main.MainFragment

class ScheduleActivity : AppCompatActivity() {
    lateinit var binding : ActivityScheduleBinding
    lateinit var dayBtnRecyclerAdapter : DayBtnRecyclerAdapter
    private var dayCount :Int = 0
    var scheduleViewPager2Adapter = ScheduleViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
        setFragment()
    }


    private fun initRecycler(){
        //일정 일수
        dayCount = 6
        if (dayCount > 7) {
            dayBtnRecyclerAdapter = DayBtnRecyclerAdapter(7, scheduleViewPager2Adapter)
            dayCount = 7
        }
        else dayBtnRecyclerAdapter = DayBtnRecyclerAdapter(dayCount, scheduleViewPager2Adapter)
        //day 버튼 클릭시 해당 날짜의 일정 fragment 띄우기
        dayBtnRecyclerAdapter.setOnItemClickListener(object : DayBtnRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                binding.scheduleActivityViewPager.setCurrentItem(position, true)
            }
        })
        binding.dayBtnList.apply {
            adapter = dayBtnRecyclerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            setHasFixedSize(true)
            addItemDecoration(HorizontalItemDecorator(5))
        }
    }

    private fun setFragment(){
        binding.scheduleActivityViewPager.apply {
            adapter = scheduleViewPager2Adapter
            isUserInputEnabled = false
            offscreenPageLimit = 6
        }
    }
}

class DayBtnRecyclerAdapter(private val num : Int, private var viewPagerAdapter: ScheduleViewPagerAdapter): RecyclerView.Adapter<DayBtnRecyclerAdapter.Holder>() {
    private val colorList = mutableListOf<Int>(
        R.color.red,
        R.color.orange,
        R.color.yellow,
        R.color.green,
        R.color.blue,
        R.color.navy,
        R.color.purple
    )
    var selectPos = 0

    interface OnItemClickListener{
        fun onItemClick(pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class Holder(val binding: RecyclerZipScheduleDayBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.daybtn.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, colorList[position])
            binding.daybtn.text = "day${position+1}"
            viewPagerAdapter.addFragment(ScheduleFragment(position))

            binding.daybtn.setOnClickListener{
                var beforePos = selectPos
                selectPos = position
                notifyItemChanged(beforePos)
                notifyItemChanged(selectPos)
                listener?.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerZipScheduleDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
        if(selectPos == position){
            holder.binding.daybtn.setHeights(150)
            holder.binding.daybtn.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.jpc))
        } else{
            holder.binding.daybtn.setHeights(100)
            holder.binding.daybtn.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.bg))
        }
    }

    override fun getItemCount(): Int {
        return num
    }
}

class ScheduleViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    var fragments : ArrayList<Fragment> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return if(fragments.size <= 7) fragments.size
        else 7
    }
    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }
}

fun View.setHeights(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}