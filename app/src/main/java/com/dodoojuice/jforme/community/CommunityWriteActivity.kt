package com.dodoojuice.jforme.community

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivityCommunityWriteBinding
import com.dodoojuice.jforme.main.MainFragment
import com.dodoojuice.jforme.main.MainRecyclerItem

class CommunityWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityWriteBinding
    lateinit var communityWriteViewModel: CommunityWriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityWriteBinding.inflate(layoutInflater)
        initViewPager()
        setContentView(binding.root)

        communityWriteViewModel = ViewModelProvider(this)[CommunityWriteViewModel::class.java]
        /*communityWriteViewModel.currentValue.observe(this, Observer {
            binding.cosText.text = it.toString()
        })*/

    }
    private fun initViewPager(){
        var viewPager2Adapter = CommunityWriteViewPagerAdapter(this)
        viewPager2Adapter.addFragment(CommunityWriteFragment(0))
        binding.addBtn.setOnClickListener{
            var currentPage = binding.viewPager.currentItem
            if(currentPage >= 6)
                Toast.makeText(this, "최대 페이지 수는 7입니다.", Toast.LENGTH_SHORT).show()
            else {
                if(viewPager2Adapter.itemCount < 7) {
                    viewPager2Adapter.addFragment(CommunityWriteFragment(viewPager2Adapter.itemCount))
                    communityWriteViewModel.updateCurrentSize()
                }
                binding.viewPager.setCurrentItem(currentPage + 1, true)
            }
        }
        binding.viewPager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                }
            })
        }
        //등록 버튼 -> 제목, fragment별 데이터 모아서 mutableList로 불러오기
        binding.wrtieButton.setOnClickListener{
            var title = binding.title.text.toString()
            //제목 기입하지 않으면 default 값으로 지정
            if (title == "") title = "제목을 입력하세요."
            val itemList = viewPager2Adapter.getFragmentItem(this)
            Log.d("해치웠나????", title + itemList.toString())
        }
    }
    class CommunityWriteViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        var fragments : ArrayList<Fragment> = ArrayList()

        override fun getItemCount(): Int {
            return if(fragments.size <= 7) fragments.size
            else 7
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
        fun addFragment(fragment: Fragment){
            fragments.add(fragment)
            notifyItemInserted(fragments.size-1)
        }
        fun removeFragment(){
            fragments.removeLast()
            notifyItemRemoved(fragments.size)
        }
        fun getFragmentItem(context : Context): MutableList<CommunityWriteItem> {
            var getMutableList = mutableListOf<CommunityWriteItem>()
            for(i in 0 until fragments.size) {
                val fragment = createFragment(i) as CommunityWriteFragment
                val item = fragment.getTextItem()
                if(item != null) getMutableList.add(item)
                else Toast.makeText(context, "사진을 등록한 부분만 업로드됩니다.", Toast.LENGTH_SHORT).show()
            }
            return getMutableList
        }
    }
}
data class CommunityWriteItem(var img: Uri, var text : String)

class CommunityWriteViewModel : ViewModel(){
    private val _currentSize = MutableLiveData<Int>()
    val currentSize:LiveData<Int>
        get() = _currentSize

    init {
        _currentSize.value = 1
    }
    fun updateCurrentSize(){
        _currentSize.value = _currentSize.value?.plus(1)
    }
}

