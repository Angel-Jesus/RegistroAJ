package com.example.registroaj.dialogoption

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.ScreenConfProd
import com.google.android.material.textfield.TextInputEditText
import java.lang.IllegalStateException

class MyDialogChange(private val posicion: Int, private val accion:String):DialogFragment(){

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_change_p,null))
            alertDialog.setPositiveButton("Actualizar"){btn,_ ->
                val txtdato = dialog?.findViewById<TextInputEditText>(R.id.edit_data)
                val dato = txtdato!!.text.toString()
                when(accion){
                    "c" -> {DataHotel.prod.changeData(dato,posicion,accion)}//ChangeData
                    else -> {DataHotel.prod.changeData(dato,posicion,accion)}
                }
                btn.cancel()
                startActivity(Intent(requireActivity(),ScreenConfProd::class.java))
            }
            alertDialog.setNegativeButton("Cancelar"){btn,_ ->
                btn.cancel()
            }
            alertDialog.create()
        }?: throw IllegalStateException("Activity is null")
    }
}