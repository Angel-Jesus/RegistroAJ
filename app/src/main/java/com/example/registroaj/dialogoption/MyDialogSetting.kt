package com.example.registroaj.dialogoption

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.ScreenHabitaciones
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.google.android.material.textfield.TextInputEditText

class MyDialogSetting: DialogFragment() {
    val listHabitaciones = arrayListOf("101","102","103","104","105","106","107","108","201","202","203","204","205","206","207")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_setting,null))
            alertDialog.setPositiveButton("Actualizar precio"){btn,_ ->

                val habitacion = dialog?.findViewById<TextInputEditText>(R.id.edit_nhabitacion)
                val precio = dialog?.findViewById<TextInputEditText>(R.id.edit_preciochange)
                val precio_habitacion = DataHotel.prefer.getTxtHabitaciones()
                var j = 0
                for(i in 0 until listHabitaciones.size){
                    if(habitacion?.text.toString() == listHabitaciones[i]){
                        precio_habitacion[i] = "S/.${precio?.text.toString()}"
                        break
                    }else{
                        j = -1
                    }
                }
                if(j == -1){
                    Toast.makeText(requireContext(),"El numero de habitación ingresado no existe",Toast.LENGTH_SHORT).show()
                }
                DataHotel.prefer.savetxtHabitaciones(precio_habitacion)
                startActivity(Intent(requireContext(), ScreenHabitaciones::class.java))
                btn.cancel()
            }
            alertDialog.setNegativeButton("Salir"){ salir,_ ->
                salir.cancel()
            }
            alertDialog.create()
        }?:throw IllegalStateException("Activity is null!")
    }
}