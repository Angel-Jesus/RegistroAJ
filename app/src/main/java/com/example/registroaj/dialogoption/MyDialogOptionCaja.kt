package com.example.registroaj.dialogoption

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.google.android.material.textfield.TextInputEditText
import java.lang.IllegalStateException

class MyDialogOptionCaja:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_change_caja,null))
            alertDialog.setPositiveButton("Actualizar"){btn,_ ->
                val txtcaja = dialog?.findViewById<TextInputEditText>(R.id.edit_data_caja)
                val caja = txtcaja!!.text.toString()
                DataHotel.venta.changeCaja(caja)
                Toast.makeText(requireContext(),"Caja inicial actualizada: S/. $caja, presionar Actualizar caja",Toast.LENGTH_SHORT).show()
                btn.cancel()
            }
            alertDialog.setNegativeButton("Cancelar"){btn,_ ->
                btn.cancel()
            }
            alertDialog.create()
        }?:throw IllegalStateException("Activity is null")
    }
}