package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val startButton = findViewById<Button>(R.id.startbtn)
        startButton.setOnClickListener { // Start MainActivity when the start button is clicked
            val intent = Intent(this@Menu, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
