package com.example.registroaj.datospicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

//El dialogFrament tiene toda la logica para mostrar los datos que deseamos obtener, osea la funcion del calendario esta incluido en esta funcion
class datosDeFecha(val listener: (day:Int, month:Int, year:Int) -> Unit): DialogFragment(),
    DatePickerDialog.OnDateSetListener{

    //Cuando se seleccione una fecha se llama a esta funcion
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //Este metodo llama a este listener
        listener(dayOfMonth,month, year) //Este listener ejecutara el codigo en el principal que esta llamado mostrarFecha()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendario = Calendar.getInstance()
        val day: Int = calendario.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendario.get(Calendar.MONTH)
        val year: Int = calendario.get(Calendar.YEAR)

        //Convertimos la actividad donde se ejecutara el codigo como contexto,
        // el this es porque ya se implemento el fragment y luego pasamos las variables
        val picker = DatePickerDialog(activity as Context, this, year, month, day)
        return picker
    }

}