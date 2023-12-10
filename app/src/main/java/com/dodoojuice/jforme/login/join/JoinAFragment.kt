package com.dodoojuice.jforme.login.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentJoinABinding

class JoinAFragment : Fragment() {
    lateinit var navController: NavController
    private val viewModelActivity: JoinViewModel by activityViewModels()
    private lateinit var binding: FragmentJoinABinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinABinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(binding.root)
        binding.nextbtn.setOnClickListener{
            viewModelActivity.setEmail(binding.mail.text.toString())
            navController.navigate(R.id.action_joinAFragment_to_joinBFragment)
        }
    }

}
