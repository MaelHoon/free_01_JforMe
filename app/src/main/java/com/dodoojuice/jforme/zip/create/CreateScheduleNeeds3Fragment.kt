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
import com.dodoojuice.jforme.databinding.FragmentCreateScheduleNeeds3Binding

class CreateScheduleNeeds3Fragment : Fragment() {
    lateinit var navController: NavController
    lateinit var binding : FragmentCreateScheduleNeeds3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateScheduleNeeds3Binding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextBtn.setOnClickListener{
            navController.navigate(R.id.action_createScheduleNeeds3Fragment_to_createScheduleInputFragment)
        }

        binding.fastBtn.setOnClickListener{
            binding.fastBtn.isSelected = true
            binding.slowBtn.isSelected = false
        }
        binding.slowBtn.setOnClickListener {
            binding.slowBtn.isSelected = true
            binding.fastBtn.isSelected = false
        }
    }
}