package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inzy.databinding.ActivityLetterCancelBinding
import com.example.inzy.databinding.ActivityLetterCancelWelcomeBinding
import com.example.inzy.databinding.ActivityReitanWelcomeBinding

class LetterCancelWelcome : AppCompatActivity() {

    private lateinit var binding: ActivityLetterCancelWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterCancelWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButtoneasy.setOnClickListener {
            val intent1 = Intent(this, LetterCancel::class.java)
            startActivity(intent1)
        }
        binding.playButtonmedium.setOnClickListener {
            val intent2 = Intent(this, LetterCancelMedium::class.java)
            startActivity(intent2)
        }
        binding.playButtonhard.setOnClickListener {
            val intent3= Intent(this, LetterCancelHard::class.java)
            startActivity(intent3)
        }
    }
}