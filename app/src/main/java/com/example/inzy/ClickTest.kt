package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import kotlin.random.Random

class ClickTest : AppCompatActivity() {

    private var score = 0
    private lateinit var startTime: Date
    private lateinit var timer: CountDownTimer
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_test)

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val tapButton: Button = findViewById(R.id.tapButton)
        val mainLayout = findViewById<RelativeLayout>(R.id.mainRelativeLayout)


        startTime = Calendar.getInstance().time

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the timer every second
                scoreTextView.text = "Punkty: $score\nCzas: ${calculateElapsedTime()} sekund"
            } override fun onFinish() {
                // Timer finished, save score and end game


            }
        }.start()



        tapButton.setOnClickListener {
            // Increment the score when the button is clicked
            score++
            scoreTextView.text = "Score: $score"

            // Move the button to a random position
            moveButtonRandomly(tapButton)

            // Check if the user has reached 10 points
            if (score >= 10) {
                endGame()
            }
        }

        mainLayout.setOnTouchListener{_, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                val x = event.x
                val y = event.y
                if (x < tapButton.x || x > tapButton.x + tapButton.width ||
                        y < tapButton.y ||  y > tapButton.y + tapButton.height){
                    score--
                    scoreTextView.text = "Punkty: $score"
                }
            }
            true
        }

    }

    private fun calculateElapsedTime(): String {
        val currentTime = Calendar.getInstance().time
        val elapsedTime = (currentTime.time - startTime.time) / 1000.0 // Convert to seconds
        return String.format(Locale.US,"%.2f", elapsedTime)
    }

    private fun moveButtonRandomly(button: Button) {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val randomX = Random.nextInt(screenWidth - button.width)
        val randomY = Random.nextInt(screenHeight - button.height)

        button.x = randomX.toFloat()
        button.y = randomY.toFloat()
    }

    private fun endGame() {
        var timeTaken = calculateElapsedTime()
        saveScore()
        val intent = Intent(this, ClickTestResult::class.java)
        intent.putExtra("timeTaken", timeTaken)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        timer.cancel() // Cancel the timer to avoid memory leaks
    }
    override fun onBackPressed() {
    }
    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("CircleClickerTest")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "time" to calculateElapsedTime(),
            "timestamp" to timestamp
        )

        scoresCollection.add(scoreData)
            .addOnSuccessListener { documentReference ->
                println("Score saved with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error saving score: $e")
            }
    }
}