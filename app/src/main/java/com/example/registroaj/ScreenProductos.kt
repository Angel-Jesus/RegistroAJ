package com.example.registroaj

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.registroaj.databinding.ActivityScreenProductosBinding
import com.example.registroaj.dialogoption.MyDialogCP
import com.example.registroaj.dialogoption.MyDialogOptionId
import com.example.registroaj.dialogoption.MyDialogOptionPassword
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.recycleviewP.ProductAdapter

class ScreenProductos : AppCompatActivity() {

    private lateinit var binding: ActivityScreenProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar()

        val datos = DataHotel.prod.getProduct()
        println(datos)
        binding.recyclerProductos.layoutManager = GridLayoutManager(this,3)
        binding.recyclerProductos.adapter = ProductAdapter(datos){onClickListener(it)}

    }

    private fun onClickListener(arrayP:ArrayList<String>) {
        MyDialogCP(arrayP).show(supportFragmentManager,"DialogP")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_productos,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.error_id ->{
                MyDialogOptionId().show(supportFragmentManager,"dialog id")
                return true
            }

            R.id.historial ->{
                startActivity(Intent(this,ScreenHistorialProductos::class.java))
                return true
            }

            R.id.vender ->{
                startActivity(Intent(this,ScreenSellProduct::class.java))
                return true
            }
            R.id.configuracion_productos ->{
                val data = DataHotel.venta.getVenta()
                MyDialogOptionPassword(0,data.toString()).show(supportFragmentManager,"Product")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun actionBar(){
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Productos"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,MainActivity::class.java))
        return true
    }
}