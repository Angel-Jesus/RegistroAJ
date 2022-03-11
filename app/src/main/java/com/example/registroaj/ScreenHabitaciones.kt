package com.example.registroaj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.registroaj.databinding.ActivityScreenHabitacionesBinding

class ScreenHabitaciones : AppCompatActivity() {
    private lateinit var binding: ActivityScreenHabitacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenHabitacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para el toolbar de regreso
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Habitaciones Disponibles"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)

    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}