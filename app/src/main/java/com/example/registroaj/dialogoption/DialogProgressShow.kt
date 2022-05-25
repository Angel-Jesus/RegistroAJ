package com.example.registroaj.dialogoption

import android.app.Activity
import android.app.AlertDialog
import com.example.registroaj.R

class DialogProgressShow(val myActivity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun starLoading(){
        val dialogView = myActivity.layoutInflater.inflate(R.layout.view_progress,null)
        val builder = AlertDialog.Builder(myActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun closeLoading(){
        isDialog.dismiss()
    }
}