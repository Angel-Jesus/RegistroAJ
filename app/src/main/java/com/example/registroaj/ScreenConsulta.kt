package com.example.registroaj

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.adaptador.ClientesAdapter
import com.example.registroaj.databinding.ActivityScreenConsultaBinding
import com.example.registroaj.dialogoption.MyDialogOption
import com.example.registroaj.dialogoption.MyDialogOptionPassword
import com.example.registroaj.funcioneshttp.VolleySingleton
import com.example.registroaj.internetconection.CheckNetworkConnection
import org.json.JSONObject
import java.util.*

class ScreenConsulta : AppCompatActivity() {
    private lateinit var binding: ActivityScreenConsultaBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var monto = 0

    @RequiresApi(Build.VERSION_CODES.M)
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
                        binding.spinnerMes.isGone = true
                        binding.completar.isGone = true
                        binding.btnFiltro.isGone = true
                        binding.btnLimpiar.isGone = true
                        if(check_Connectivity().not()){
                            show_alert_Connection()
                            binding.progressBar.isGone = true
                        }else{
                            binding.progressBar.isVisible = true
                        }
                        monto = 0
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
                            monto = 0
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
                            monto = 0
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
                            monto = 0
                            filtrar(txtfiltro,position,select)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this, { isConnected ->
            if(isConnected.not()){
                show_alert_Connection()
                binding.progressBar.isGone = true
            }else{
                Toast.makeText(this,"Conectado a internet",Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnLimpiar.setOnClickListener {
            binding.spinnerFiltro.setSelection(0)
            binding.editCompletar.setText("")
        }

        binding.btnIngreso.setOnClickListener {
            MyDialogOptionPassword(monto,"Monto").show(supportFragmentManager, "mydialogoptionmonto")
        }

    }

    private fun show_alert_Connection(){
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Conexion fallida, compruebe la conexion a internet")
        alert.setCancelable(true)
        alert.setPositiveButton("salir"){btn, _ ->
            btn.cancel()
        }
        alert.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun check_Connectivity():Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
    }

    private fun filtrar(typeFiltro: String, pos: Int, pos_fecha:Int) {
        VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos(typeFiltro, pos, pos_fecha))
    }

    private fun cliente_datos(type:String, pos: Int, pos_fecha: Int): JsonObjectRequest {
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1pc00XXcYE7gKapkw24tRiKf46SdreeZzvb43FEGFlhI&sheet=formulario1"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response, type, pos, pos_fecha)
            }, { Response.ErrorListener { Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    private fun parseObject(response: JSONObject, filtro:String, position:Int, pos_fecha: Int) {
        binding.recyclerClientes.layoutManager = LinearLayoutManager(this)
        val jArrayFormulario = response.getJSONArray("formulario1")
        val size = jArrayFormulario.length()
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
                        monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
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
                            monto += index.getString("Precio").toInt()
                            j++
                        }
                    }
                }
            }
        }
        binding.progressBar.isGone = true
        binding.recyclerClientes.adapter = ClientesAdapter(arrayForm){onItemSelected(it)}
        binding.cantidad.text = arrayForm.size.toString()

    }

    fun onItemSelected(clientesDatos: clientesDatos){
        MyDialogOption(clientesDatos).show(supportFragmentManager, "mydialogoption")
    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,MainActivity::class.java))
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_loading, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.loading ->{
                binding.spinnerMes.isGone = true
                binding.completar.isGone = true
                binding.btnFiltro.isGone = true
                binding.btnLimpiar.isGone = true
                binding.progressBar.isVisible = true
                filtrar("",0,0)
                return true
            }
            R.id.Datos_mes -> {
                startActivity(Intent(this,ScreenEstadistica::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}