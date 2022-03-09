package com.example.registroaj.datospicker

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class datosHora(val listener: (String) -> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {


    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val hour:String
        if(hourOfDay < 10){
            hour = "0$hourOfDay"
        }else{
            hour = hourOfDay.toString()
        }
        listener("$hour:$minute")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar:Calendar = Calendar.getInstance()
        val hour:Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute:Int = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context, this, hour, minute, false)
        return dialog
    }
}