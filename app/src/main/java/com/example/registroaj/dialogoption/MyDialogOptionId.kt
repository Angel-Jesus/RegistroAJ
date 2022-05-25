package com.example.registroaj.dialogoption

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.google.android.material.textfield.TextInputEditText
import java.lang.IllegalStateException

class MyDialogOptionId: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_change_id,null))
            alertDialog.setPositiveButton("Actualizar id"){btn,_ ->
                val txtid = dialog?.findViewById<TextInputEditText>(R.id.edit_id_producto)
                val id = txtid!!.text.toString()
                DataHotel.venta.changeId(id)
                btn.cancel()
            }

            alertDialog.setNegativeButton("Cancelar"){btn,_ ->
                btn.cancel()
            }
            alertDialog.create()
        }?:throw IllegalStateException("Activity is null")
    }
}