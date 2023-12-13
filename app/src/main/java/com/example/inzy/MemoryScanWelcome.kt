package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inzy.databinding.ActivityLetterCancelBinding
import com.example.inzy.databinding.ActivityLetterCancelWelcomeBinding
import com.example.inzy.databinding.ActivityMemoryScanWelcomeBinding
import com.example.inzy.databinding.ActivityReitanWelcomeBinding

class MemoryScanWelcome : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryScanWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryScanWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButtoneasy.setOnClickListener {
            val intent1 = Intent(this, MemoryScan::class.java)
            startActivity(intent1)
        }
        binding.playButtonmedium.setOnClickListener {
            val intent2 = Intent(this, MemoryScanMedium::class.java)
            startActivity(intent2)
        }
        binding.playButtonhard.setOnClickListener {
            val intent3= Intent(this, MemoryScanHard::class.java)
            startActivity(intent3)
        }
    }
}