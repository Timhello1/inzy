package com.example.inzy

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.pow
import kotlin.random.Random

class SwitchTrailTestView : View {

    private val circleCount = 25
    private val circles = mutableListOf<Circle>()

    private var revealedCircles = 1

    private var startTime: Long = 0
    private var endTime: Long = 0

    private var selectedCircle: Circle? = null

    private val paint = Paint()

    private var clickableCircleNumber = 1

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    constructor(context: Context) : super(context) {
        init()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (revealedCircles <= circleCount){
                    if (startTime == 0L) {
                        // Record the start time on the first click
                        startTime = SystemClock.elapsedRealtime()
                    }

                    val clickedCircle = findClickableCircleAtPosition(event.x,event.y)
                    clickedCircle?.let {
                        if(it.number == clickableCircleNumber){
                            clickableCircleNumber++
                            revealedCircles++
                            if (clickableCircleNumber > circleCount) {
                                endTime = SystemClock.elapsedRealtime()
                                // Otwórz nowe Activity, gdy wszystkie punkty są pokolorowane
                                openNextActivity()
                            }
                            invalidate()
                            generateCircles(width, height)
                        }
                    }
                }
            }
        }
        return true
    }

    private fun openNextActivity() {

        val timeTaken = endTime - startTime
        saveScore()
        // Tutaj otwórz nowe Activity
        val context = context
        if (context is AppCompatActivity) {
            val intent = Intent(context, ReitanTestResult::class.java)
            intent.putExtra("timeTaken", timeTaken)
            context.startActivity(intent)
        }
    }

    private fun isPointInCircle(x: Float, y: Float, circle: Circle): Boolean {
        val distance = Math.sqrt(
            (x - circle.x).toDouble().pow(2.0) +
                    (y - circle.y).toDouble().pow(2.0)
        )
        return distance < circle.radius
    }

    private fun findClickableCircleAtPosition(x: Float, y: Float): Circle? {
        for (circle in circles) {
            if (circle.number == clickableCircleNumber && isPointInCircle(x, y, circle)) {
                return circle
            }
        }
        return null
    }



    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        generateCircles(w, h)
    }

    private fun generateCircles(viewWidth: Int, viewHeight: Int) {
        circles.clear()

        val radius = 50.toFloat()
        for (i in 1..circleCount) { // Start the loop from 1
            val color = if (i <= revealedCircles) Color.BLUE else Color.TRANSPARENT
            circles.add(Circle(0f, 0f, radius, i, color))
        }

        for (i in 0 until circleCount) {
            setCirclePosition(i, viewWidth, viewHeight)
        }



        invalidate()
    }

    private fun setCirclePosition(index: Int, viewWidth: Int, viewHeight: Int) {
        val radius = circles[index].radius
        var x: Float
        var y: Float

        // Ensure that the circles do not overlap
        do {
            x = Random.nextInt(50, viewWidth - 50).toFloat()
            y = Random.nextInt(50, viewHeight - 50).toFloat()
        } while (isColliding(x, y, radius))

        circles[index] = circles[index].copy(x = x, y = y)
    }

    private fun isColliding(newX: Float, newY: Float, newRadius: Float): Boolean {
        for (circle in circles) {
            val distance = Math.sqrt(
                (newX - circle.x).toDouble().pow(2.0) +
                        (newY - circle.y).toDouble().pow(2.0)
            )
            if (distance < newRadius + circle.radius) {
                // Circles are colliding
                return true
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until circleCount) {
            val circle = circles[i]
            paint.color = circle.color
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint)
            drawCircleNumber(canvas, circle)
        }
    }

    private fun drawCircleNumber(canvas: Canvas, circle: Circle) {
        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        canvas.drawText(circle.number.toString(), circle.x, circle.y + circle.radius / 4, textPaint)
    }

    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("SwitchTrailTest")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "time" to (endTime - startTime)/1000,
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

    data class Circle(val x: Float, val y: Float, val radius: Float, val number: Int, var color: Int = Color.BLUE)
}
