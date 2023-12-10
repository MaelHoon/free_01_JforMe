package com.dodoojuice.jforme.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.dodoojuice.jforme.databinding.FragmentMypageBinding
import com.dodoojuice.jforme.login.join.JoinActivity
import com.dodoojuice.jforme.main.MainFragment
import com.dodoojuice.jforme.main.tapfragment.MainTourFragment
import com.google.android.material.tabs.TabLayoutMediator

class MypageFragment : Fragment() {
    lateinit var binding : FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        initOnClickListener()
        initViewPager()
        return binding.root
    }
    private fun initViewPager(){
        var viewPager2MypageAdapter = MainFragment.ViewPager2Adapter(this)
        viewPager2MypageAdapter.addFragment(MypageRecordFragment())
        viewPager2MypageAdapter.addFragment(MypageReviewFragment())

        binding.viewPager.apply {
            adapter = viewPager2MypageAdapter
            registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(binding.tlMypage, binding.viewPager){
                tab, position ->
            when(position){
                0 -> tab.text = "기록"
                1 -> tab.text = "후기"
            }
        }.attach()
    }
    private fun initOnClickListener(){
        binding.settingImg.setOnClickListener {
            //setting Activity
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}