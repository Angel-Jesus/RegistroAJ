package com.example.registroaj

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.databinding.ActivityScreenEstadisticaBinding
import com.example.registroaj.estadistica.Estadistica
import com.example.registroaj.estadistica.Procedencia
import com.example.registroaj.estadistica.adaptadorE.EstadisticaAdapter
import com.example.registroaj.funcioneshttp.VolleySingleton
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ScreenEstadistica : AppCompatActivity() {

    private lateinit var binding: ActivityScreenEstadisticaBinding

    var Tabla:TableLayout? = null
    var tabla_fecha = Estadistica.count_inicial
    val day_txt = Estadistica.days

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenEstadisticaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showActionBar()
        binding.progress.isGone = false
        getCount()

    }

    private fun getCount() {
        VolleySingleton.getInstance(this).addToRequestQueue(Count(this))
    }

    private fun Count(context: Context):JsonObjectRequest{
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1pc00XXcYE7gKapkw24tRiKf46SdreeZzvb43FEGFlhI&sheet=formulario1"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response)
            }, { Response.ErrorListener { Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    private fun parseObject(response: JSONObject?) {
        val jArrayFormulario = response?.getJSONArray("formulario1")
        val size = jArrayFormulario?.length()
        val calendar = Calendar.getInstance()
        var month = calendar.get(Calendar.MONTH) + 1
        var year = calendar.get(Calendar.YEAR)
        var month_string:String
        if(calendar.get(Calendar.DAY_OF_MONTH) < 28){
            if(month == 1){
                month_string = "12"
                year -= 1
            }else{
                month -= 1
                month_string = if(month < 10){
                    "0$month"
                }else{
                    month.toString()
                }
            }
        }else{
            month_string = month.toString()
        }

        var month_year = "$month_string/$year"
        var j = 0
        var triple_count = 0
        var fechas = arrayListOf("")
        var lugar = arrayListOf("")
        fechas.clear()
        lugar.clear()

        for(i in 0 until size!!){
            var index = jArrayFormulario.getJSONObject(i)

            if(index.getString("Fecha").substring(3,10).equals(month_year)){
                fechas.add(j,index.getString("Fecha").substring(0,2))
                lugar.add(j,index.getString("Procedencia").replace(" ",""))
                if(index.getString("N°_habitaciones") == "201"){triple_count++}
                j++
            }
        }
        val filtro_fecha = fechas.groupingBy {it}.eachCount().filter {it.value>0}
        val total_fecha = fechas.size

        val filtro_lugar = lugar.groupingBy {it}.eachCount().filter {it.value>0}
        val total_lugar = filtro_lugar.size

        val sum_count_lugar = filtro_lugar.values.toIntArray().sum()
        val keys_lugar = filtro_lugar.keys.toTypedArray()

        var lugarList = arrayListOf(Procedencia("",0))
        lugarList.clear()
        var p = 0
        for(i in 0 until 4){
            for(j in 0 until 8){
                p++
                if(p < 32){
                    if(p < 10){
                       tabla_fecha[i][j] = if(filtro_fecha["0$p"] != null){filtro_fecha["0$p"].toString()}else{"0"}
                    }
                    else{
                        tabla_fecha[i][j] = if(filtro_fecha[p.toString()] != null){filtro_fecha[p.toString()].toString()}else{"0"}
                    }
                }else{
                    tabla_fecha[i][j] = total_fecha.toString()
                }
            }
        }

        for(i in 0 until (total_lugar)){
            lugarList.add(i, Procedencia(keys_lugar[i],filtro_lugar[keys_lugar[i]]!!.toInt()))
        }

        lugarList.add(total_lugar, Procedencia("TOTAL",sum_count_lugar))

        //Crear tabla de los dias del mes
        builTable()

        binding.recyclerLugar.layoutManager = LinearLayoutManager(this)
        binding.recyclerLugar.adapter = EstadisticaAdapter(lugarList)

        binding.dobleCount.text = (total_fecha-triple_count).toString()
        binding.tripleCount.text = triple_count.toString()
        binding.txtdoble.isGone = false
        binding.dobleCount.isGone = false
        binding.txtxtriple.isGone = false
        binding.tripleCount.isGone = false
        binding.progress.isGone = true
        binding.linearLayout.isGone = false
    }

    private fun builTable() {
        Tabla = binding.TablaList
        Tabla?.removeAllViews()
         for(i in (0 until tabla_fecha.count())){
            val register = LayoutInflater.from(this).inflate(R.layout.tabla_row_estadistica,null,false)
             val txt1 = register.findViewById<View>(R.id.day) as TextView
             val txt2 = register.findViewById<View>(R.id.day2) as TextView
             val txt3 = register.findViewById<View>(R.id.day3) as TextView
             val txt4 = register.findViewById<View>(R.id.day4) as TextView
             val txt5 = register.findViewById<View>(R.id.day5) as TextView
             val txt6 = register.findViewById<View>(R.id.day6) as TextView
             val txt7 = register.findViewById<View>(R.id.day7) as TextView
             val txt8 = register.findViewById<View>(R.id.day8) as TextView

             val count1 = register.findViewById<View>(R.id.day_count) as TextView
             val count2 = register.findViewById<View>(R.id.day_count2) as TextView
             val count3 = register.findViewById<View>(R.id.day_count3) as TextView
             val count4 = register.findViewById<View>(R.id.day_count4) as TextView
             val count5 = register.findViewById<View>(R.id.day_count5) as TextView
             val count6 = register.findViewById<View>(R.id.day_count6) as TextView
             val count7 = register.findViewById<View>(R.id.day_count7) as TextView
             val count8 = register.findViewById<View>(R.id.day_count8) as TextView

             txt1.text = day_txt[i][0]
             txt2.text = day_txt[i][1]
             txt3.text = day_txt[i][2]
             txt4.text = day_txt[i][3]
             txt5.text = day_txt[i][4]
             txt6.text = day_txt[i][5]
             txt7.text = day_txt[i][6]
             txt8.text = day_txt[i][7]

             count1.text = tabla_fecha[i][0]
             count2.text = tabla_fecha[i][1]
             count3.text = tabla_fecha[i][2]
             count4.text = tabla_fecha[i][3]
             count5.text = tabla_fecha[i][4]
             count6.text = tabla_fecha[i][5]
             count7.text = tabla_fecha[i][6]
             count8.text = tabla_fecha[i][7]

             Tabla?.addView(register)
         }
    }

    private fun showActionBar() {
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Estadistica Mensual"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,ScreenConsulta::class.java))
        return true
    }


}