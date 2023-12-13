package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.inzy.databinding.ActivityClickTestBinding
import com.example.inzy.databinding.ActivityClickTestResultBinding

class ClickTestResult : AppCompatActivity() {

    private lateinit var binding: ActivityClickTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClickTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timeTaken = intent.getStringExtra("timeTaken")
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "Test skończony w ${timeTaken} seconds"

        binding.goBackMenu.setOnClickListener {
            val goMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(goMenuIntent)
        }
    }
    override fun onBackPressed() {
    }
}