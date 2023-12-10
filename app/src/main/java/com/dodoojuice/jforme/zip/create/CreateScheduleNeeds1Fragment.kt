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

class CreateScheduleNeeds1Fragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleNeeds1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleNeeds1Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleNeeds1Fragment_to_createScheduleNeeds2Fragment)
        }
        binding.coreBack.setOnClickListener {
            binding.coreBack.isSelected = true
            binding.hotBack.isSelected = false
        }
        binding.hotBack.setOnClickListener{
            binding.coreBack.isSelected = false
            binding.hotBack.isSelected = true
        }
    }
}