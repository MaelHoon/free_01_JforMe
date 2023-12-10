package com.dodoojuice.jforme.zip.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleNeeds1Binding
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleNeeds2Binding


class CreateScheduleNeeds2Fragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleNeeds2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleNeeds2Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleNeeds2Fragment_to_createScheduleNeeds3Fragment)
        }
        binding.yesBtn.setOnClickListener{
            binding.yesBtn.isSelected = true
            binding.noBtn.isSelected = false
        }
        binding.noBtn.setOnClickListener {
            binding.noBtn.isSelected = true
            binding.yesBtn.isSelected = false
        }
    }
}