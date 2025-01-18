package com.example.proteamfantasy11rj.tudo_game

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity2 : AppCompatActivity() {
    private lateinit var button1: Button
    private lateinit var button2 :Button

    private lateinit var LeneyarLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)


        button1= findViewById(R.id.button1)
        button2=findViewById(R.id.button2)
        LeneyarLayout=findViewById(R.id.main)

        button1.setOnClickListener {


            LeneyarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            Toast.makeText(applicationContext,"Save blue",Toast.LENGTH_SHORT).show()
        }
        button2.setOnClickListener {
            LeneyarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Toast.makeText(this,"Save red",Toast.LENGTH_SHORT).show()
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}