package com.dodoojuice.jforme.zip.create

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleTimerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateScheduleTimerFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleTimerBinding

    private lateinit var startDateTextView: TextView
    private lateinit var  endDateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleTimerFragment_to_createScheduleSetFragment)
        }

        // 타임 피커의 시간 변경 리스너 설정
        binding.timepicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            val selectedTime = "$hourOfDay:$minute"
            // 선택한 시간을 변수에 저장하거나 필요한 작업을 수행
            //"시작 시간: $selectedTime"
        }
        binding.timepicker2.setOnTimeChangedListener { view, hourOfDay, minute ->
            val selectedTime = "$hourOfDay:$minute"
            // 선택한 시간을 변수에 저장하거나 필요한 작업을 수행
            //"끝 시간: $selectedTime")
        }

        startDateTextView = binding.godayshow
        endDateTextView = binding.comedayshow


        binding.calendar.setOnClickListener {
            showDatePickerDialog()
        }
    }
    private  fun showDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _,selectedYear, selectedMonth, selectedDay ->
                val selectedDate = formatDate(selectedYear, selectedMonth, selectedDay)
                updateDate(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String{
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd일", Locale.getDefault())
        return dateFormat.format(calendar.time)

    }

    private fun updateDate(selectedDate: String) {
        if (startDateTextView.text.toString() == "나타나라 얍"){
            startDateTextView.text = selectedDate
        } else{
            endDateTextView.text = selectedDate
        }
    }
}