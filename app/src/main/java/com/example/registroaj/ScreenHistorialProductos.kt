package com.example.registroaj

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.databinding.ActivityScreenHistorialProductosBinding
import com.example.registroaj.dialogoption.MyDialogOptionCaja
import com.example.registroaj.funcioneshttp.VolleySingleton
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.productosDatos.ProductosListVenta
import com.example.registroaj.productos.recycleviewP.ProductosListVAdapter
import org.json.JSONObject

class ScreenHistorialProductos : AppCompatActivity() {

    private lateinit var binding: ActivityScreenHistorialProductosBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenHistorialProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar()
        VolleySingleton.getInstance(this).addToRequestQueue(getDataVenta("Recycle"))
        binding.btnActualizar.setOnClickListener { actualizar() }
        binding.caja.setOnClickListener { MyDialogOptionCaja().show(supportFragmentManager,"caja") }
        binding.caja.text = "S/. ${DataHotel.venta.getCaja()}"
    }

    private fun actualizar(){
        val alert = AlertDialog.Builder(this)
        alert.setMessage("¿Seguro que desea actualizar?")
        alert.setCancelable(true)
        alert.setPositiveButton("Si"){btn,_ ->
            VolleySingleton.getInstance(this).addToRequestQueue(getDataVenta("id"))
            btn.cancel()
        }
        alert.setNegativeButton("No"){btn, _ ->
            btn.cancel()
        }
        alert.show()
    }

    private fun getDataVenta(accion:String): JsonObjectRequest {
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1AiXz1H-ZHdDEqVZ4Way4DiJoWFOcMfovYD775_EAH6k&sheet=formularioventa"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response,accion)
            }, { Response.ErrorListener { Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    @SuppressLint("SetTextI18n")
    private fun parseObject(response: JSONObject, action:String) {
        val jArrayVenta = response.getJSONArray("formularioventa")
        val size = jArrayVenta.length()
        val id = DataHotel.venta.getID()!!.toInt()
        var caja = DataHotel.venta.getCaja()!!.toFloat()

        when(action){
            "Recycle" ->{
                val arrayFormV = arrayListOf(ProductosListVenta("","","",""))
                arrayFormV.clear()
                var j = 0

                for(i in 0 until size){
                    val index = jArrayVenta.getJSONObject(i)
                    if(index.getString("id").toInt() > id){
                        arrayFormV.add(j, ProductosListVenta(
                            index.getString("Fecha"),
                            index.getString("Producto"),
                            index.getString("Precio"),
                            index.getString("Cantidad")
                            )
                        )
                        caja += (index.getString("Cantidad").toFloat())*(index.getString("Precio").toFloat())
                        j++
                    }
                }
                binding.progressBarV.isGone = true
                binding.recycleHistorial.layoutManager = LinearLayoutManager(this)
                binding.recycleHistorial.adapter = ProductosListVAdapter(arrayFormV)
                binding.caja.text = "S/. $caja"
            }

            "id" ->{
                val index = jArrayVenta.getJSONObject(size - 1)
                val id_new = index.getString("id")
                DataHotel.venta.changeId(id_new)
                startActivity(Intent(this,ScreenHistorialProductos::class.java))
            }
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
        startActivity(Intent(this,ScreenProductos::class.java))
        return true
    }
}