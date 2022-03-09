package com.example.registroaj

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.adaptador.ClientesAdapter
import com.example.registroaj.databinding.ActivityScreenConsultaBinding
import com.example.registroaj.funcioneshttp.VolleySingleton
import org.json.JSONObject
import java.util.*

class ScreenConsulta : AppCompatActivity() {
    private lateinit var binding: ActivityScreenConsultaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenConsultaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para el toolbar de regreso
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)

        val lista_filtro = listOf("Aplicar Filtro", "Procedencia", "Mes", "DNI")
        val adaptadorFiltro = ArrayAdapter(this,R.layout.spinner_item_filtro,lista_filtro)
        binding.spinnerFiltro.adapter = adaptadorFiltro

        val lista_mes = listOf("Seleccionar Mes","Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio","Agosto", "Septiembre", "Octubre", "Noviembre","Diciembre")
        val adaptadorMes = ArrayAdapter(this,R.layout.spinner_item_filtro,lista_mes)
        binding.spinnerMes.adapter = adaptadorMes

        binding.spinnerFiltro.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long){
                when(position){
                    0 -> {
                        //Toast.makeText(this@ScreenConsulta,"si funciona",Toast.LENGTH_SHORT).show()
                        binding.spinnerMes.isGone = true
                        binding.completar.isGone = true
                        binding.btnFiltro.isGone = true
                        binding.btnLimpiar.isGone = true
                        binding.progressBar.isVisible = true
                        filtrar("",position,0)
                    }

                    1 ->{
                        binding.spinnerMes.isVisible = true
                        binding.completar.isVisible = true
                        binding.btnFiltro.isVisible = true
                        binding.btnLimpiar.isVisible = true
                        binding.btnFiltro.setOnClickListener {
                            val txtfiltro = binding.editCompletar.text.toString()
                            val select = binding.spinnerMes.selectedItemPosition
                            binding.progressBar.isVisible = true
                            filtrar(txtfiltro,position,select)
                        }
                    }

                    2 ->{
                        binding.completar.isGone = true
                        binding.spinnerMes.isVisible = true
                        binding.btnFiltro.isVisible = true
                        binding.btnLimpiar.isVisible = true
                        binding.btnFiltro.setOnClickListener {
                            val select = binding.spinnerMes.selectedItemPosition
                            binding.progressBar.isVisible = true
                            filtrar("",position,select)
                        }
                    }

                    3 -> {
                        binding.spinnerMes.isVisible = true
                        binding.completar.isVisible = true
                        binding.btnFiltro.isVisible = true
                        binding.btnLimpiar.isVisible = true
                        binding.btnFiltro.setOnClickListener {
                            val select = binding.spinnerMes.selectedItemPosition
                            val txtfiltro = binding.editCompletar.text.toString()
                            binding.progressBar.isVisible = true
                            filtrar(txtfiltro,position,select)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Toast.makeText(this@ScreenConsulta,"no se para que funciona",Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnLimpiar.setOnClickListener {
            binding.spinnerFiltro.setSelection(0)
            binding.editCompletar.setText("")
        }



    }

    fun filtrar(typeFiltro: String, pos: Int, pos_fecha:Int) {
        VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos(this,typeFiltro, pos, pos_fecha))
    }

    private fun cliente_datos(context: Context, type:String, pos: Int, pos_fecha: Int): JsonObjectRequest {
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1pc00XXcYE7gKapkw24tRiKf46SdreeZzvb43FEGFlhI&sheet=formulario1"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response, type, pos, pos_fecha)
            }, { Response.ErrorListener { Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    private fun parseObject(response: JSONObject, filtro:String, position:Int, pos_fecha: Int) {
        val jArrayFormulario = response.getJSONArray("formulario1")
        val size = jArrayFormulario.length()
        binding.recyclerClientes.layoutManager = LinearLayoutManager(this)
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val Filtro = filtro.lowercase() //Convertir toda la cadena en minuscula para evitar errores de filtraje
        val fecha: String
        //Conversion de mont y year Int a formato String de fecha
        if(month < 10){
            fecha = "0$month/$year"
        }else {
            fecha = "$month/$year"
        }
        val pos_fechaS: String
        //Conversion de mes seleccionado Int a String
        if(pos_fecha < 10){
            pos_fechaS = "0$pos_fecha"
        }else {
            pos_fechaS = "$pos_fecha"
        }

        var arrayForm = arrayListOf(clientesDatos("","","","","","","","",""))
        arrayForm.clear()

        var j = 0
        for(i in 0 until size){
            var index = jArrayFormulario.getJSONObject(i)
            when(position){
                0 -> {
                    if(index.getString("Fecha").substring(3,10).equals(fecha)){
                        arrayForm.add(j, clientesDatos(
                            index.getString("N°_habitaciones"),
                            index.getString("Fecha"),
                            index.getString("Hora"),
                            index.getString("Apellidos_y_Nombres"),
                            index.getString("DNI"),
                            index.getString("Precio"),
                            index.getString("Procedencia"),
                            index.getString("Observaciones"),
                            index.getInt("id").toString()
                        )
                        )
                        j++
                    }
                }
                1 -> {
                    if(pos_fecha == 0){
                        if(index.getString("Procedencia").lowercase() == Filtro){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else if(pos_fecha <= month){
                        if(index.getString("Procedencia").lowercase() == Filtro && index.getString("Fecha").substring(3,10).equals("$pos_fechaS/$year")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else{
                        if(index.getString("Procedencia").lowercase() == Filtro && index.getString("Fecha").substring(3,10).equals("$pos_fechaS/${year-1}")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }

                }

                2 -> {
                    if(pos_fecha == 0){
                        if(index.getString("Fecha").substring(3,5).equals(pos_fechaS)){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else if(pos_fecha <= month){
                        if(index.getString("Fecha").substring(3,10).equals("$pos_fechaS/$year")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else{
                        if(index.getString("Fecha").substring(3,10).equals("$pos_fechaS/${year-1}")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                }

                3 -> {
                    if(pos_fecha == 0){
                        if(index.getString("DNI").equals(Filtro)){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else if(pos_fecha <= month){
                        if(index.getString("DNI").equals(Filtro) && index.getString("Fecha").substring(3,10).equals("$pos_fechaS/$year")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                    else{
                        if(index.getString("DNI").equals(Filtro) && index.getString("Fecha").substring(3,10).equals("$pos_fechaS/${year-1}")){
                            arrayForm.add(j, clientesDatos(
                                index.getString("N°_habitaciones"),
                                index.getString("Fecha"),
                                index.getString("Hora"),
                                index.getString("Apellidos_y_Nombres"),
                                index.getString("DNI"),
                                index.getString("Precio"),
                                index.getString("Procedencia"),
                                index.getString("Observaciones"),
                                index.getInt("id").toString()
                            )
                            )
                            j++
                        }
                    }
                }
            }
        }
        binding.progressBar.isGone = true
        binding.recyclerClientes.adapter = ClientesAdapter(arrayForm) { onItemSelected(it) }
        binding.cantidad.text = arrayForm.size.toString()
    }

    fun onItemSelected(clientesDatos: clientesDatos){
        MyDialogOption(clientesDatos).show(supportFragmentManager, "mydialogoption")
    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}