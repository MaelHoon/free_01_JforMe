package com.dodoojuice.jforme.zip.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleInputBinding
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleNeeds1Binding

class CreateScheduleInputFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleInputBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleInputFragment_to_createScheduleTimerFragment)
        }
        //seekbar 드래그하면 숫자 변함
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val step = 5
                val newValue = (progress / step) * step
                binding.priceEdit.setText(newValue.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // 사용자가 SeekBar를 터치하고 움직이기 시작할 때 실행할 코드
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 사용자가 SeekBar 터치를 끝낼 때 실행할 코드
            }
        })
    }
}