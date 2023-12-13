package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LetterCancelMedium : AppCompatActivity() {
    private lateinit var targetLetter: String
    private lateinit var letterList: MutableList<String>
    private var initialCorrectGuessCount: Int = 0
    private var timeInSeconds: Long = 0
    private lateinit var timer: CountDownTimer
    private var foundLetters: Int = 0
    private var secondTimerDuration: Long = 60000  // Set the duration of the second timer (60 seconds)
    private lateinit var secondTimer: CountDownTimer  // Add a new timer variable
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_cancel_medium)

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        val targetLetterTextView: TextView = findViewById(R.id.targetLetterTextView)


        targetLetter = getRandomLetter()

        // Set the target letter below the grid
        targetLetterTextView.text = "Szukana litera: $targetLetter"

        letterList = generateLetterList()

        // Check if the target letter appears 5 times
        while (letterList.count { it == targetLetter } < 8) {
            // Replace a random non-target letter with the target letter
            val randomIndex = letterList.indexOfFirst { it != targetLetter }
            if (randomIndex != -1) {
                letterList[randomIndex] = targetLetter
            } else {
                // Generate a new random index and try again
                val newRandomIndex = (0 until letterList.size).random()
                letterList[newRandomIndex] = targetLetter
            }
        }

        // Check if there are more than 5 occurrences of the target letter
        while (letterList.count { it == targetLetter } > 8) {
            // Replace the target letter with a random non-target letter
            val randomIndex = letterList.indexOfFirst { it == targetLetter }
            letterList[randomIndex] = getRandomLetter()
        }

        letterList.shuffle()


        // Define the 8x12 grid
        val rows = 12
        val cols = 8

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                // Create a TextView for each cell
                val cell = createCell(i, j, letterList[i * cols + j])
                // Add the TextView to the GridLayout
                gridLayout.addView(cell)
            }
        }
        initialCorrectGuessCount = letterList.count { it == targetLetter }


        // Initialize and start the timer
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInSeconds++
            }

            override fun onFinish() {
                // Handle timer finish if needed
            }
        }.start()

        secondTimer = object : CountDownTimer(secondTimerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Handle tick if needed
            }

            override fun onFinish() {
                // Move to ResultActivity when the second timer finishes
                navigateToResultActivity()
            }
        }.start()

    }

    private fun generateLetterList(): MutableList<String>{
        val list = MutableList(12 * 8) { getRandomLetter() }
        // Count initial occurrences of the target letter
        initialCorrectGuessCount = list.count { it == targetLetter }
        return list
    }


    private fun createCell(row: Int, col: Int, letter: String): TextView {
        val cell = TextView(this)
        val params = GridLayout.LayoutParams()
        params.rowSpec = GridLayout.spec(row)
        params.columnSpec = GridLayout.spec(col)
        params.width = 120
        params.height = 120
        cell.layoutParams = params
        cell.text = letter
        cell.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        cell.gravity = Gravity.CENTER
        cell.setBackgroundResource(R.drawable.border) // You can customize the background if needed
        cell.setOnClickListener { onCellClick(it as TextView) }
        return cell
    }

    private fun getRandomLetter(): String {
        // Add logic to get a random letter (A-Z) as needed
        val randomChar = ('A'..'Z').random()
        return randomChar.toString()
    }

    private fun onCellClick(cell: TextView) {
        // Handle cell click event here
        if (cell.text == targetLetter) {
            // The clicked letter matches the target letter
            // Add your logic for the correct guess (e.g., change background color)
            cell.setBackgroundResource(R.drawable.borderclick)
            foundLetters += 1
            if (foundLetters == 8) {
                navigateToResultActivity()
            }
        }

    }

    private fun navigateToResultActivity() {
        saveScore()
        // Stop the timer
        timer.cancel()
        secondTimer.cancel()

        // Create an Intent to navigate to ResultActivity
        val intent = Intent(this, LetterResultActivity::class.java)

        // Pass data to ResultActivity
        intent.putExtra("foundLetters", foundLetters)
        intent.putExtra("timeInSeconds", timeInSeconds)

        // Start ResultActivity
        startActivity(intent)
        finish() // Optional: Finish the current activity if you don't want to go back to it
    }
    override fun onBackPressed() {
    }
    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("LetterCancel8Score")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "score" to foundLetters,
            "time" to timeInSeconds,
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