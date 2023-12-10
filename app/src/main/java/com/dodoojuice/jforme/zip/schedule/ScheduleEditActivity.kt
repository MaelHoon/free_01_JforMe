package com.dodoojuice.jforme.zip.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivityScheduleEditBinding

class ScheduleEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityScheduleEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
