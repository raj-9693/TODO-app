package com.example.proteamfantasy11rj.tudo_game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var player1EditText: TextInputEditText
    private lateinit var player2EditText: TextInputEditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        player1EditText = findViewById(R.id.player1EditText)
        player2EditText = findViewById(R.id.player2EditText)
        submitButton = findViewById(R.id.submitButton)

        // Set click listener for submit button
        submitButton.setOnClickListener {
            val player1Name = player1EditText.text?.toString()?.trim()
            val player2Name = player2EditText.text?.toString()?.trim()

Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show()

            if (player1Name.isNullOrEmpty() || player2Name.isNullOrEmpty()) {
                Toast.makeText(this, "Please enter both player names", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start GameActivity with player names
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("PLAYER1_NAME", player1Name)
                putExtra("PLAYER2_NAME", player2Name)
            }
            startActivity(intent)
        }
    }
}