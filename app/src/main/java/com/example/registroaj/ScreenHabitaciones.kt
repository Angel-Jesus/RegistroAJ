package com.example.registroaj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        /*
        val dato = intent.getStringExtra("registro_dato")

        val n_habitaciones = listOf("101","102","103","104","105","106","107","108","201","202","203","204","205","206","207")
        val direcciones_imagen = listOf(
            binding.h101,
            binding.h102,
            binding.h103,
            binding.h104,
            binding.h105,
            binding.h106,
            binding.h107,
            binding.h108,
            binding.h201,
            binding.h202,
            binding.h203,
            binding.h204,
            binding.h205,
            binding.h206,
            binding.h207
            )
        val direcciones_txt = listOf(
            binding.L101,
            binding.L102,
            binding.L103,
            binding.L104,
            binding.L105,
            binding.L106,
            binding.L107,
            binding.L108,
            binding.L201,
            binding.L202,
            binding.L203,
            binding.L204,
            binding.L205,
            binding.L206,
            binding.L207
        )
        for(i in n_habitaciones.indices){
            if(dato == n_habitaciones[i]){
                direcciones_imagen[i].setImageResource(R.drawable.habitacion_off)
                direcciones_txt[i].setText(R.string.HO)
                break
            }
        }
        */
        binding.h101.setOnClickListener{
            if(binding.L101.text.equals("Ocupado")){
                binding.h101.setImageResource(R.drawable.habitacion_on)
                binding.L101.setText(R.string.Hd)
            }else{
                binding.h101.setImageResource(R.drawable.habitacion_off)
                binding.L101.setText(R.string.HO)
            }

        }

        binding.h102.setOnClickListener{
            if(binding.L102.text.equals("Ocupado")){
                binding.h102.setImageResource(R.drawable.habitacion_on)
                binding.L102.setText(R.string.Hd)
            }else{
                binding.h102.setImageResource(R.drawable.habitacion_off)
                binding.L102.setText(R.string.HO)
            }
        }

        binding.h103.setOnClickListener{
            if(binding.L103.text.equals("Ocupado")){
                binding.h103.setImageResource(R.drawable.habitacion_on)
                binding.L103.setText(R.string.Hd)
            }else{
                binding.h103.setImageResource(R.drawable.habitacion_off)
                binding.L103.setText(R.string.HO)
            }
        }

        binding.h104.setOnClickListener{
            if(binding.L104.text.equals("Ocupado")){
                binding.h104.setImageResource(R.drawable.habitacion_on)
                binding.L104.setText(R.string.Hd)
            }else{
                binding.h104.setImageResource(R.drawable.habitacion_off)
                binding.L104.setText(R.string.HO)
            }
        }

        binding.h105.setOnClickListener{
            if(binding.L105.text.equals("Ocupado")){
                binding.h105.setImageResource(R.drawable.habitacion_on)
                binding.L105.setText(R.string.Hd)
            }else{
                binding.h105.setImageResource(R.drawable.habitacion_off)
                binding.L105.setText(R.string.HO)
            }
        }

        binding.h106.setOnClickListener{
            if(binding.L106.text.equals("Ocupado")){
                binding.h106.setImageResource(R.drawable.habitacion_on)
                binding.L106.setText(R.string.Hd)
            }else{
                binding.h106.setImageResource(R.drawable.habitacion_off)
                binding.L106.setText(R.string.HO)
            }
        }

        binding.h107.setOnClickListener{
            if(binding.L107.text.equals("Ocupado")){
                binding.h107.setImageResource(R.drawable.habitacion_on)
                binding.L107.setText(R.string.Hd)
            }else{
                binding.h107.setImageResource(R.drawable.habitacion_off)
                binding.L107.setText(R.string.HO)
            }
        }

        binding.h108.setOnClickListener{
            if(binding.L108.text.equals("Ocupado")){
                binding.h108.setImageResource(R.drawable.habitacion_on)
                binding.L108.setText(R.string.Hd)
            }else{
                binding.h108.setImageResource(R.drawable.habitacion_off)
                binding.L108.setText(R.string.HO)
            }
        }

        binding.h201.setOnClickListener{
            if(binding.L201.text.equals("Ocupado")){
                binding.h201.setImageResource(R.drawable.habitacion_on)
                binding.L201.setText(R.string.Hd)
            }else{
                binding.h201.setImageResource(R.drawable.habitacion_off)
                binding.L201.setText(R.string.HO)
            }
        }

        binding.h202.setOnClickListener{
            if(binding.L202.text.equals("Ocupado")){
                binding.h202.setImageResource(R.drawable.habitacion_on)
                binding.L202.setText(R.string.Hd)
            }else{
                binding.h202.setImageResource(R.drawable.habitacion_off)
                binding.L202.setText(R.string.HO)
            }
        }

        binding.h203.setOnClickListener{
            if(binding.L203.text.equals("Ocupado")){
                binding.h203.setImageResource(R.drawable.habitacion_on)
                binding.L203.setText(R.string.Hd)
            }else{
                binding.h203.setImageResource(R.drawable.habitacion_off)
                binding.L203.setText(R.string.HO)
            }
        }

        binding.h204.setOnClickListener{
            if(binding.L204.text.equals("Ocupado")){
                binding.h204.setImageResource(R.drawable.habitacion_on)
                binding.L204.setText(R.string.Hd)
            }else{
                binding.h204.setImageResource(R.drawable.habitacion_off)
                binding.L204.setText(R.string.HO)
            }
        }

        binding.h205.setOnClickListener{
            if(binding.L205.text.equals("Ocupado")){
                binding.h205.setImageResource(R.drawable.habitacion_on)
                binding.L205.setText(R.string.Hd)
            }else{
                binding.h205.setImageResource(R.drawable.habitacion_off)
                binding.L205.setText(R.string.HO)
            }
        }

        binding.h206.setOnClickListener{
            if(binding.L206.text.equals("Ocupado")){
                binding.h206.setImageResource(R.drawable.habitacion_on)
                binding.L206.setText(R.string.Hd)
            }else{
                binding.h206.setImageResource(R.drawable.habitacion_off)
                binding.L206.setText(R.string.HO)
            }
        }

        binding.h207.setOnClickListener{
            if(binding.L207.text.equals("Ocupado")){
                binding.h207.setImageResource(R.drawable.habitacion_on)
                binding.L207.setText(R.string.Hd)
            }else{
                binding.h207.setImageResource(R.drawable.habitacion_off)
                binding.L207.setText(R.string.HO)
            }
        }

    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}