package com.dodoojuice.jforme

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter.setOnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.dodoojuice.jforme.community.CommunityFragment
import com.dodoojuice.jforme.databinding.ActivityMainBinding
import com.dodoojuice.jforme.login.LoginActivity
import com.dodoojuice.jforme.main.MainFragment
import com.dodoojuice.jforme.mypage.MypageFragment
import com.dodoojuice.jforme.wish.WishFragment
import com.dodoojuice.jforme.zip.ZipFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var mainFrag : MainFragment? = null
    private var zipFrag : ZipFragment? = null
    private var communityFrag : CommunityFragment? = null
    private var wishFrag : WishFragment? = null
    private var myPageFrag : MypageFragment? = null
    private val fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        //Set Home Fragment
        mainFrag = MainFragment()
        fragmentManager.beginTransaction().replace(R.id.framelayout, mainFrag!!).commit()

        binding.mainFragBtn.setOnClickListener{
            binding.bottomnavigation.isSelected = false
            if(mainFrag == null){
                mainFrag = MainFragment()
                fragmentManager.beginTransaction().add(R.id.framelayout, mainFrag!!).commit()
            }
            if(mainFrag != null) fragmentManager.beginTransaction().show(mainFrag!!).commit()
            if(zipFrag != null) fragmentManager.beginTransaction().hide(zipFrag!!).commit()
            if(communityFrag != null) fragmentManager.beginTransaction().hide(communityFrag!!).commit()
            if(wishFrag != null) fragmentManager.beginTransaction().hide(wishFrag!!).commit()
            if(myPageFrag != null) fragmentManager.beginTransaction().hide(myPageFrag!!).commit()
        }
        binding.bottomnavigation.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.mydrawer_fragment -> {
                        if(zipFrag == null){
                            zipFrag = ZipFragment()
                            fragmentManager.beginTransaction().add(R.id.framelayout, zipFrag!!).commit()
                        }
                        if(mainFrag != null) fragmentManager.beginTransaction().hide(mainFrag!!).commit()
                        if(zipFrag != null) fragmentManager.beginTransaction().show(zipFrag!!).commit()
                        if(communityFrag != null) fragmentManager.beginTransaction().hide(communityFrag!!).commit()
                        if(wishFrag != null) fragmentManager.beginTransaction().hide(wishFrag!!).commit()
                        if(myPageFrag != null) fragmentManager.beginTransaction().hide(myPageFrag!!).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.community_fragment -> {
                        if(communityFrag == null){
                            communityFrag = CommunityFragment()
                            fragmentManager.beginTransaction().add(R.id.framelayout, communityFrag!!).commit()
                        }
                        if(mainFrag != null) fragmentManager.beginTransaction().hide(mainFrag!!).commit()
                        if(zipFrag != null) fragmentManager.beginTransaction().hide(zipFrag!!).commit()
                        if(communityFrag != null) fragmentManager.beginTransaction().show(communityFrag!!).commit()
                        if(wishFrag != null) fragmentManager.beginTransaction().hide(wishFrag!!).commit()
                        if(myPageFrag != null) fragmentManager.beginTransaction().hide(myPageFrag!!).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.wishilst_fragment -> {
                        if(wishFrag == null){
                            wishFrag = WishFragment()
                            fragmentManager.beginTransaction().add(R.id.framelayout, wishFrag!!).commit()
                        }
                        if(mainFrag != null) fragmentManager.beginTransaction().hide(mainFrag!!).commit()
                        if(zipFrag != null) fragmentManager.beginTransaction().hide(zipFrag!!).commit()
                        if(communityFrag != null) fragmentManager.beginTransaction().hide(communityFrag!!).commit()
                        if(wishFrag != null) fragmentManager.beginTransaction().show(wishFrag!!).commit()
                        if(myPageFrag != null) fragmentManager.beginTransaction().hide(myPageFrag!!).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.mypage_fragment -> {
                        if(myPageFrag == null){
                            myPageFrag = MypageFragment()
                            fragmentManager.beginTransaction().add(R.id.framelayout, myPageFrag!!).commit()
                        }
                        if(mainFrag != null) fragmentManager.beginTransaction().hide(mainFrag!!).commit()
                        if(zipFrag != null) fragmentManager.beginTransaction().hide(zipFrag!!).commit()
                        if(communityFrag != null) fragmentManager.beginTransaction().hide(communityFrag!!).commit()
                        if(wishFrag != null) fragmentManager.beginTransaction().hide(wishFrag!!).commit()
                        if(myPageFrag != null) fragmentManager.beginTransaction().show(myPageFrag!!).commit()
                        return@setOnItemSelectedListener true
                    }
                }
                true
            }
        }
    }
}