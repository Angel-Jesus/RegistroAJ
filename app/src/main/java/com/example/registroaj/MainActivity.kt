package com.example.registroaj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RegistroAJ)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val btnRegistro: Button = findViewById(R.id.registro)
        val btnConsulta: Button = findViewById(R.id.consulta)
        val btnHabitaciones: Button = findViewById(R.id.habitaciones)

        btnRegistro.setOnClickListener {
            startActivity(Intent(this, ScreenRegistro::class.java))
        }

        btnConsulta.setOnClickListener {
            startActivity(Intent(this,ScreenConsulta::class.java))
        }

        btnHabitaciones.setOnClickListener {
            startActivity(Intent(this, ScreenHabitaciones::class.java))
        }
    }
}