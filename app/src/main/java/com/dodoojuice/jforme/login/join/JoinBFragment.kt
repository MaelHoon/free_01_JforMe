package com.dodoojuice.jforme.login.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentJoinABinding
import com.dodoojuice.jforme.databinding.FragmentJoinBBinding

class JoinBFragment : Fragment() {

    lateinit var navController: NavController
    private val viewModelActivity: JoinViewModel by activityViewModels()
    private lateinit var binding: FragmentJoinBBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinBBinding.inflate(inflater,container,false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.nextbtn2.setOnClickListener{
            if(binding.pw.text.toString() == binding.repw.text.toString()) {
                viewModelActivity.setPassword(binding.pw.text.toString())
                navController.navigate(R.id.action_joinBFragment_to_joinCFragment)
            }
            else Toast.makeText(requireContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
        }
    }

}
