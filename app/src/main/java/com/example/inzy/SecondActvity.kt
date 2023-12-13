package com.example.inzy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActvity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_actvity)

        val button: Button = findViewById(R.id.button)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewSecond)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val elements = ArrayList<String>()
        elements.add("Wyniki testu zwinności ")
        elements.add("Wyniki testu Stroop'a ")
        elements.add("Wyniki testu Reitan'a ")
        elements.add("Wyniki testu zmiany ścieżki ")
        elements.add("Wyniki testu skanu pamięci (łatwy) ")
        elements.add("Wyniki testu skanu pamięci (średni) ")
        elements.add("Wyniki testu skanu pamięci (trudny) ")
        elements.add("Wyniki testu szyfrowania (łatwy) ")
        elements.add("Wyniki testu szyfrowania (średni) ")
        elements.add("Wyniki testu szyfrowania (trudny) ")
        elements.add("Wyniki testu anulowania liter (łatwy) (punkty) ")
        elements.add("Wyniki testu anulowania liter (średni) (punkty) ")
        elements.add("Wyniki testu anulowania liter (trudny) (punkty) ")
        elements.add("Wyniki testu anulowania liter (łatwy) (czas) ")
        elements.add("Wyniki testu anulowania liter (średni) (czas) ")
        elements.add("Wyniki testu anulowania liter (trudny) (czas) ")
        elements.add("Wyniki testu anulowania liter (łatwy) (złożenie) ")
        elements.add("Wyniki testu anulowania liter (średni) (złożenie) ")
        elements.add("Wyniki testu anulowania liter (trudny) (złożenie) ")

        button.setOnClickListener {
            val menuIntent = Intent(this, MainActivity::class.java)
            startActivity(menuIntent)

        }

        val adapter = MyAdapterSecond(this, elements)
        recyclerView.adapter = adapter
    }
}
