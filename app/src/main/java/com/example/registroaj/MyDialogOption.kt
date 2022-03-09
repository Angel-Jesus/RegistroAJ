package com.example.registroaj

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.example.registroaj.funcioneshttp.enviarDatosUrl
import com.google.android.material.textfield.TextInputEditText

class MyDialogOption(private val datos: clientesDatos): DialogFragment(){
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_option,null))
            alertDialog.setPositiveButton("Actualizar Registro") { _, _ ->
                val txt = dialog?.findViewById<TextInputEditText>(R.id.edit_cambio)
                val cambio = txt?.text.toString()
                val registro = arrayListOf(datos.habitacion,datos.fecha, datos.hora, datos.AyN,datos.dni,datos.precio,datos.procedencia,datos.Observacion,datos.id)
                for(i in registro.indices){
                    if(registro[i] == "C"){
                        registro[i] = cambio
                        break
                    }
                }
                enviarDatosUrl(requireContext(), registro, progress = ProgressBar(requireContext())).modificar()
            }
            alertDialog.setNegativeButton("Eliminar Registro") { _, _ ->
                val modificaciones = arrayListOf<String>()
                modificaciones.addAll(listOf(" ", " ", " ", " ", " ", " ", " ", " ", datos.id))
                enviarDatosUrl(requireContext(), modificaciones, progress = ProgressBar(requireContext())).eliminar()
            }
            alertDialog.create()
        }?:throw IllegalStateException("Activity is null!")

    }
}