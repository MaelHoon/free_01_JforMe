package com.dodoojuice.jforme.login.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.database.RetrofitInstance.apiService
import com.dodoojuice.jforme.database.SignupRequest
import com.dodoojuice.jforme.databinding.FragmentJoinCBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class JoinCFragment : Fragment() {
    lateinit var navController: NavController
    private val viewModelActivity: JoinViewModel by activityViewModels()
    private lateinit var binding: FragmentJoinCBinding
    lateinit var yearSpinner : NumberPicker
    lateinit var monthSpinner : NumberPicker
    lateinit var daySpinner : NumberPicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinCBinding.inflate(inflater,container,false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setData()

        binding.finishBtn.setOnClickListener{
            val selectedYear = yearSpinner.value
            val selectedMonth = monthSpinner.value
            val selectedDay = daySpinner.value
            var selectedDate = selectedYear.toString()
            selectedDate += if(selectedMonth<10) "0$selectedMonth"
            else selectedMonth.toString()
            selectedDate += if (selectedDay < 10) "0$selectedDay"
            else selectedDay.toString()
            viewModelActivity.setBirthday(selectedDate)

            viewModelActivity.setNickname(binding.nickname.text.toString())
            if(binding.male.isSelected) viewModelActivity.setSex(1)
            else viewModelActivity.setSex(2)
            if(viewModelActivity.nickname.value!="" &&viewModelActivity.sex.value!=0 && viewModelActivity.birthday.value!="") {
                lifecycleScope.launch {
                    signUser(
                        viewModelActivity.email.value!!,
                        viewModelActivity.nickname.value!!,
                        viewModelActivity.pw.value!!,
                        1,
                        viewModelActivity.birthday.value!!
                    )
                }
                requireActivity().finish()
            }
            else Toast.makeText(requireContext(), "입력사항을 모두 기입하여 주십시오.", Toast.LENGTH_LONG).show()
        }
    }
    private fun setData(){
        //viewModelActivity.setBirthday("20000724")
        yearSpinner = binding.yearSpinner
        monthSpinner = binding.monthSpinner
        daySpinner = binding.daySpinner
        // 년도
        yearSpinner.minValue = 1970
        yearSpinner.maxValue = 2010
        yearSpinner.value = 2000
        // 월
        monthSpinner.minValue = 1
        monthSpinner.maxValue = 12
        // 일
        daySpinner.minValue = 1
        daySpinner.maxValue = 31

        yearSpinner.displayedValues = (1970..2010).map { it.toString() + " 년" }.toTypedArray()
        monthSpinner.displayedValues = (1..12).map { it.toString() + " 월" }.toTypedArray()
        daySpinner.displayedValues = (1..31).map { it.toString() + " 일" }.toTypedArray()

    }


    private suspend fun signUser(email: String, nick: String, pw: String, gender: Int, birthday :String) {
        val signUpRequest = SignupRequest( email, nick, pw, gender, birthday)

        try {
            val response = apiService.signUpUser(signUpRequest)

            if (response.isSuccessful) {
                // Successful sign-up
                val userData = response.body()
                // Process the user data
            } else {
                // Handle sign-up error
                Log.e("SignUpResponse", "Error: ${response.code()}")
                // Display an error message to the user
            }
        } catch (e: Exception) {
            // Handle network or other errors
            Log.e("SignUpResponse", "Error: ${e.message}")
            // Display an error message to the user
        }
    }


}
