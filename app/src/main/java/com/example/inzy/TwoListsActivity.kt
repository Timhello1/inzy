package com.example.inzy

// TwoListsActivity.kt
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class TwoListsActivity : AppCompatActivity() {

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var handler: Handler? = null
    private val timeUpdateInterval = 1000 // Update time every second
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    private fun highlightMismatchedDigits(randomDigits: String, userInput: String): SpannableString {
        val spannableString = SpannableString(randomDigits)

        for (i in userInput.indices) {
            val randomDigit = randomDigits[i]
            val userDigit = userInput[i]

            if (randomDigit != userDigit) {
                val startIndex = i
                val endIndex = i + 1
                spannableString.setSpan(
                    ForegroundColorSpan(Color.RED),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        return spannableString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_lists)

        // Record the start time when the activity is created
        startTime = System.currentTimeMillis()
        startTimer()

        val data1 = listOf('a', 'd', 's', 'f', 'h', 'g', 'l', 'j', 'k', 'x')
        val data2 = (0..9).toList()

        val recyclerView1: RecyclerView? = findViewById(R.id.recyclerView1)
        val recyclerView2: RecyclerView? = findViewById(R.id.recyclerView2)
        val randomDigitsTextView: TextView? = findViewById(R.id.randomDigitsTextView)
        val translationEditText: EditText? = findViewById(R.id.translationEditText)
        val checkButton: Button? = findViewById(R.id.checkButton)

        if (recyclerView1 != null && recyclerView2 != null && randomDigitsTextView != null  && translationEditText != null && checkButton != null) {
            recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView1.adapter = MyAdapter(data1)

            recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView2.adapter = MyAdapter(data2)



            // Generate a sequence of 10 randomized digits
            val randomDigits = (0 until 10).map { Random.nextInt(10) }.joinToString(" ")

            // Display the randomized digits below the RecyclerViews
            randomDigitsTextView.text = "$randomDigits"

            checkButton.setOnClickListener {
                val userInput = translationEditText.text.toString().toLowerCase()

                // Check if the user input is not empty and has exactly 10 characters
                if (userInput.length == 10) {
                    val inputIndices = userInput.mapNotNull { data1.indexOf(it) }


                    // Check if all characters in the user input are present in data1
                    if (inputIndices.all { it != -1 }) {
                        // Check if the translated sequence of indices matches the randomly generated sequence
                        val isCorrect = inputIndices.joinToString(" ") == randomDigits

                        if (isCorrect) {
                            // Handle the case when the translation is correct
                            // For example, display a message or perform an action
                            saveScore()
                            stopTimer()
                            Toast.makeText(this, "Prawidłowo! Czas: ${elapsedTime} sekund", Toast.LENGTH_SHORT).show()
                            val digitsubIntent = Intent(this, DigitSubResults::class.java)
                            digitsubIntent.putExtra("elapsedTime", elapsedTime)
                            startActivity(digitsubIntent)
                        } else {
                            // Handle the case when the translation is incorrect
                            // For example, display a message or perform an action
                            Toast.makeText(this, "Nieprawidłowa odpowiedź. Spróbuj jeszcze raz.", Toast.LENGTH_SHORT).show()
                            randomDigitsTextView.text = highlightMismatchedDigits(randomDigits, inputIndices.joinToString(" "))
                        }
                    } else {
                        // Display an error message for invalid characters in the input
                        Toast.makeText(this, "Błąd. Podaj tylko 10 liter znajdujących się w słowniku.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Display an error message for input length other than 10
                    Toast.makeText(this, "Podaj tylko 10 liter, podane zostało: " + userInput.length.toString(), Toast.LENGTH_SHORT).show()
                }
            }



        } else {
            // Log an error or show a toast if the RecyclerViews are not found
        }
    }
    // Start the timer
    private fun startTimer() {
        handler = Handler()
        handler?.post(object : Runnable {
            override fun run() {
                if (startTime != 0L) {
                    val currentTime = System.currentTimeMillis()
                    elapsedTime = (currentTime - startTime) / 1000
                    // Update your UI with the elapsed time if needed
                    // For example: elapsedTimeTextView.text = "Elapsed Time: ${elapsedTime} seconds"
                    handler?.postDelayed(this, timeUpdateInterval.toLong())
                }
            }
        })
    }

    // Stop the timer
    private fun stopTimer() {
        handler?.removeCallbacksAndMessages(null)
        handler = null
    }
    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("DigitalSub10")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "time" to elapsedTime,
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
    override fun onBackPressed() {
    }
}
