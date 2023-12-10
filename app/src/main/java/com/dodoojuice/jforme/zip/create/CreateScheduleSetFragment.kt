package com.dodoojuice.jforme.zip.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleSetBinding

class CreateScheduleSetFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleSetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleSetFragment_to_createSchedulePlaceFragment)
        }
        val spinnerA = binding.citySpinner
        val spinnerB = binding.guSpinner

        var regionData = resources.getStringArray(R.array.Region)

        val adapterA = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, regionData)
        spinnerA.adapter = adapterA

        val adapterB = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, emptyList())
        spinnerB.adapter = adapterB

        spinnerA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val selectedAItem = regionData[position]
                    val newItemsB = getItemsForA(selectedAItem)
                    val adapterB = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, newItemsB)
                    spinnerB.adapter = adapterB
                } else {

                }
            }
            private fun getItemsForA(selectedAItem: String): List<String> {
                val itemsB = when (selectedAItem) {
                    "강원도" -> listOf("춘천시","원주시","강릉시","동해시","태백시","속초시","삼척시","홍천군","횡성군","영월군","평창군","정선군","철원군","화천군","양구군","인제군","고성군","양양군")
                    "경기도" -> listOf("수원시","고양시","용인시","성남시","부천시","안산시","화성시","남양주시","안양시","평택시","의정부시","파주시","시흥시","김포시","광명시","광주시","군포시","이천시","오산시","하남시","양주시","구리시","안성시","포천시","의왕시","여주시","양평군","동두천시","과천시","가평군","연천군")
                    "서울" -> listOf("종로구","중구","용산구","성동구","광진구","동대문구","중랑구","성북구","강북구","도봉구","노원구","은평구","서대문구","마포구","양천구","강서구","구로구","금천구","영등포구","동작구","관악구","서초구","강남구","송파구","강동구")
                    "인천" -> listOf("중구","동구","미추홀구","연수구","남동구","부평구","계양구","서구","강화군","옹진군")
                    "충청남도" -> listOf("천안시","공주시","보령시","아산시","서산시","논산시","계룡시","당진시","금산군","부여군","서천군","청양군","홍성군","예산군","태안군")
                    "충청북도" -> listOf("청주시","충주시","제천시","보은군","옥천군","영동군","증편군","진천군","괴산군","음성군","단양군")
                    "세종" -> listOf("소정면","전의면","전동면","조치원읍","연서면","연동면","장군면","연기면","금남면","부강면")
                    "대전" -> listOf("동구","중구","서구","유성구","대덕구")
                    "전라남도" -> listOf("목포시","여수시","순천시","나주시","광양시","담양군","곡성군","구례군","고흥군","보성군","화순군","장흥군","강진군","해남군","영암군","무안군","함평군","영광군","장성군","완도군","진도군","신안군")
                    "전라북도" -> listOf("전주시","군산시","익산시","정읍시","남원시","김제시","완주군","진안군","무주군","장수군","임실군","순창군","고창군","부안군")
                    "광주" -> listOf("동구","서구","남구","북구","광산구")
                    "경상남도" -> listOf("창원시","진주시","통영시","사천시","김해시","밀양시","거제시","양산시","의령군","의령군","함안군","창녕군","고성군","남해군","하동군","산청군","함양군","거창군","합천군")
                    "경상북도" -> listOf("포항시”,”경주시”,”김천시”,”안동시”,”구미시","영주시","영천시","상주시","문경시","경산시","군위군","의성군","청송군","영양군","영덕군","청도군","고령군","성주군","칠곡군","예천군","봉화군","울진군","울릉군")
                    "부산" -> listOf("중구","서구","동구","영도구","부산진구","동래구","남구","북구","해운대구","사하구","금정구”","강서구","연제구","수영구","사상구","기장군")
                    "울산" -> listOf("중구","남구","동구","북구","울주군")
                    "대구" -> listOf("중구","동구","서구","남구","북구","수성구","달서구","달성군")
                    "제주도" -> listOf("제주시","서귀포시","애월읍","한림읍","한경면","대정읍","안덕면","중문","남원읍","표선면","조천읍","구좌읍","성산읍")



                    else -> emptyList()
                }
                return itemsB
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}