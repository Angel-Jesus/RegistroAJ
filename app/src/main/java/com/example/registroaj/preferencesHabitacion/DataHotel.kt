package com.example.registroaj.preferencesHabitacion

import android.annotation.SuppressLint
import android.app.Application
import com.example.registroaj.productos.productosDatos.Prod
import com.example.registroaj.productos.productosDatos.Venta

class DataHotel: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var prefer:Prefer
        lateinit var prod:Prod
        lateinit var venta:Venta
    }

    override fun onCreate() {
        super.onCreate()
        prefer = Prefer(applicationContext)
        prod = Prod(applicationContext)
        venta = Venta(applicationContext)
    }

}