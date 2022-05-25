package com.example.registroaj.dialogoption

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.ScreenConfProd
import com.google.android.material.textfield.TextInputEditText

class MyDialogOptionAdd:DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val alerDialog = AlertDialog.Builder(it)
            alerDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_add_product,null))
            alerDialog.setPositiveButton("Añadir"){btn,_ ->
                val txtp = dialog?.findViewById<TextInputEditText>(R.id.edit_p)
                val txtc = dialog?.findViewById<TextInputEditText>(R.id.edit_c)
                val txts = dialog?.findViewById<TextInputEditText>(R.id.edit_s)
                val p = txtp!!.text.toString()
                val c = txtc!!.text.toString()
                val s = txts!!.text.toString()

                DataHotel.prod.saveNewProduct(p,c,s)
                btn.cancel()
                startActivity(Intent(requireActivity(), ScreenConfProd::class.java))
            }
            alerDialog.setNegativeButton("Cancelar"){btn,_ ->
                btn.cancel()
            }
            alerDialog.create()

        }?:throw IllegalStateException("Activity is null!")
    }

}