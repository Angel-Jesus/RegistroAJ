package com.example.registroaj.dialogoption

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.productos.ScreenConfProd
import com.google.android.material.textfield.TextInputEditText

class MyDialogOptionPassword(private val monto: Int, private val filtro:String): DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_ingreso,null))
            alertDialog.setPositiveButton("Ingresar") { _, _ ->
                val password = dialog?.findViewById<TextInputEditText>(R.id.edit_password)
                val pass = password?.text.toString()
                if(pass == "siempreadelante"){
                    if(filtro == "Monto"){
                        Toast.makeText(requireContext(),"Ingreso Total: S/. $monto",Toast.LENGTH_LONG).show()
                    }else {
                        startActivity(Intent(requireActivity(),ScreenConfProd::class.java))
                    }
                }else{
                    Toast.makeText(requireContext(),"Contraseña incorrecta",Toast.LENGTH_LONG).show()
                }
            }
            alertDialog.setNegativeButton("Salir"){salir, _->
                salir.cancel()
            }

            alertDialog.create()
        }?:throw IllegalStateException("Activity is null!")

    }
}