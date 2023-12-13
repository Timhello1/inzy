package com.example.inzy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class DigitSubResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digit_sub_result)

        // Retrieve the elapsed time from the Intent
        val elapsedTime = intent.getLongExtra("elapsedTime", 0)

        // Use the elapsed time as needed
        val elapsedTimeTextView: TextView = findViewById(R.id.elapsedTimeTextView)
        elapsedTimeTextView.text = "czas: $elapsedTime sekund"

        val backToMainMenuButton: Button = findViewById(R.id.backToMainMenuButton)
        backToMainMenuButton.setOnClickListener {
            // Navigate back to the main menu or perform any other action
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(mainMenuIntent)
        }
    }
    override fun onBackPressed() {
    }

}
