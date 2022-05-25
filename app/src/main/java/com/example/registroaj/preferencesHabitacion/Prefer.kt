package com.example.registroaj.preferencesHabitacion

import android.content.Context

class Prefer(contex: Context) {
    val HABITATION_KEY = "key_Habitacion"
    val HABITATION_TXT_KEY = "key_txtHabitacion"
    val H_NUMBER = arrayListOf("101","102","103","104","105","106","107","108","201","202","203","204","205","206","207")

    val setting = contex.getSharedPreferences(HABITATION_KEY,0)
    val settingtxt = contex.getSharedPreferences(HABITATION_TXT_KEY,0)

    fun saveHabitaciones(habitaciones:ArrayList<Boolean>){
        for(i in 0 until H_NUMBER.size){
            setting.edit().putBoolean(H_NUMBER[i],habitaciones[i]).apply()
        }
    }

    fun getHabitaciones():ArrayList<Boolean>{
        var stateHabitation = arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
        for(i in 0 until H_NUMBER.size){
            stateHabitation[i] = setting.getBoolean(H_NUMBER[i],false)
        }
        return stateHabitation
    }

    fun savetxtHabitaciones(txthabitaciones:ArrayList<String>){
        for(i in 0 until H_NUMBER.size){
            settingtxt.edit().putString(H_NUMBER[i],txthabitaciones[i]).apply()
        }
    }

    fun getTxtHabitaciones():ArrayList<String>{
        var txtHabitation = arrayListOf("S/.35","S/.35","S/.35","S/.35","S/.65","S/.55","S/.45","S/.45","S/.80","S/.55","S/.45","S/.45","S/.55","S/.55","S/.55")
        for(i in 0 until H_NUMBER.size){
            if(settingtxt.getString(H_NUMBER[i],"") != ""){
                txtHabitation[i] = settingtxt.getString(H_NUMBER[i],"").toString()
            }
        }
        return txtHabitation
    }
}