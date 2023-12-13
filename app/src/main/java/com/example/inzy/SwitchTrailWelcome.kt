package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inzy.databinding.ActivitySwitchTrailWelcomeBinding

class SwitchTrailWelcome : AppCompatActivity() {

    private lateinit var binding: ActivitySwitchTrailWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwitchTrailWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            val intent = Intent(this, SwitchTrail::class.java)
            startActivity(intent)
        }
    }
}