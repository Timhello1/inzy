package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.inzy.databinding.ActivityReitanTestResultBinding

class ReitanTestResult : AppCompatActivity() {

    private lateinit var binding: ActivityReitanTestResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReitanTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timeTaken = intent.getLongExtra("timeTaken", 0)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "Test ukończony w ${timeTaken / 1000} sekund"

        binding.goBackMenu.setOnClickListener {
            val goMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(goMenuIntent)
        }
    }
    override fun onBackPressed() {
    }
}