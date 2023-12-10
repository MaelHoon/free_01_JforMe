package com.dodoojuice.jforme.main

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentMainBinding
import com.dodoojuice.jforme.databinding.FragmentMainCafeBinding
import com.dodoojuice.jforme.main.tapfragment.MainCafeFragment
import com.dodoojuice.jforme.main.tapfragment.MainExperienceFragment
import com.dodoojuice.jforme.main.tapfragment.MainFoodFragment
import com.dodoojuice.jforme.main.tapfragment.MainHotFragment
import com.dodoojuice.jforme.main.tapfragment.MainSeasonFragment
import com.dodoojuice.jforme.main.tapfragment.MainTourFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale

data class MainRecyclerItemList(var cate: String, var type : Int, var itemList : MutableList<MainRecyclerItem>)
data class MainRecyclerItem(var title: String, var mainImg: Int, var isLikeChecked : Boolean)

class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding
    private val gpsViewModel : MainGpsViewModel by viewModels()
    lateinit var permissionResultLauncher: ActivityResultLauncher<Array<String>>
    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()

        //현재 위치 set(받아올 수 없으면 서울시청)
        permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { permission -> permission.value == true }) {

            } else {
                Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        permissionResultLauncher.launch(permissions)
        requestLocationUpdate()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    requestLocationUpdate()
                } else {
                    Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult == null) {
                return
            }
            for (location in locationResult.locations) {
                if (location != null) {
                    var address: List<String> = listOf("서울특별시", "중구", "태평로1가")
                    val addrList = Geocoder(requireContext(), Locale.KOREA).getFromLocation(location.latitude, location.longitude, 1)
                    if (addrList != null) {
                        for (addr in addrList) {
                            val splitedAddr = addr.getAddressLine(0).split(" ")
                            address = splitedAddr
                        }
                    }
                    gpsViewModel.updateLatLng(location.latitude, location.longitude, "${address[2]} ${address[3]}")
                    Log.d("gg;;;;;;;;;;;;;;;", "GPS Location changed, Latitude: ${gpsViewModel.lat.value}, Longitude: ${gpsViewModel.lng.value}")
                }
            }
        }
    }
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @SuppressLint("MissingPermission")
    fun requestLocationUpdate() {

        val locationRequest = LocationRequest.create()?.apply {
            /*interval = 10000
            fastestInterval = 5000 //5초 간격으로 업데이트인데 여기선 안 써도 될 듯*/
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        /*val locationRequest = LocationRequest.Builder()
                .setIntervalMillis(10000)
                .setFastestIntervalMillis(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .build()*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (locationRequest != null) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun initViewPager(){
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(MainHotFragment())
        viewPager2Adapter.addFragment(MainTourFragment())
        viewPager2Adapter.addFragment(MainSeasonFragment())
        viewPager2Adapter.addFragment(MainFoodFragment())
        viewPager2Adapter.addFragment(MainCafeFragment())
        viewPager2Adapter.addFragment(MainExperienceFragment())

        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain){
            tab, position ->
            when(position){
                0 -> tab.text = "Hot"
                1 -> tab.text = "관광"
                2 -> tab.text = "시즌"
                3 -> tab.text = "식당"
                4 -> tab.text = "카페"
                5 -> tab.text = "체험"
            }
        }.attach()
    }

    class ViewPager2Adapter(fragment: Fragment):FragmentStateAdapter(fragment){
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

class MainGpsViewModel: ViewModel() {

    private var _lat = MutableLiveData<Double>()
    val lat: LiveData<Double>
        get() = _lat
    private var _lng = MutableLiveData<Double>()
    val lng: LiveData<Double>
        get() = _lng
    private var _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    init {
        _lat.value = 37.5667
        _lng.value = 126.9784
        _address.value = "중구 태평로1가"
    }

    fun updateLatLng(lat : Double, lng : Double, address : String) {
        _lat.value = lat
        _lng.value = lng
        _address.value = address
    }
}