package com.example.registroaj

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.databinding.ActivityScreenRegistroBinding
import com.example.registroaj.datospicker.datosDeFecha
import com.example.registroaj.datospicker.datosHora
import com.example.registroaj.dialogoption.DialogProgressShow
import com.example.registroaj.funcioneshttp.VolleySingleton
import com.example.registroaj.funcioneshttp.enviarDatosUrl
import com.example.registroaj.internetconection.CheckNetworkConnection
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class ScreenRegistro : AppCompatActivity() {
    private lateinit var binding: ActivityScreenRegistroBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    val number_hab = listOf("101","102","103","104","105","106","107","108","201","202","203","204","205","206","207")

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityScreenRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para el toolbar de regreso
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)

        if(check_Connectivity().not()){
            binding.btnGuardar.isEnabled = false
            binding.btnConsulta.isEnabled = false
            show_alert_Connection()
        }else{
            binding.btnGuardar.isEnabled = true
            binding.btnConsulta.isEnabled = true
        }

        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this, { isConnected ->
            if(isConnected){
                binding.btnGuardar.isEnabled = true
                binding.btnConsulta.isEnabled = true
            }else{
                binding.btnGuardar.isEnabled = false
                binding.btnConsulta.isEnabled = false
                show_alert_Connection()
            }
        })

        //Para escoger la hora y fecha en el registro
        binding.editFecha.setOnClickListener {mostrarFecha()}
        binding.editHora.setOnClickListener {mostrarHora()}

        //Funcion para guadar los datos
        binding.btnGuardar.setOnClickListener {
            enviar_datos()
        }

        //Funcion para ir a la ventana consular
        binding.btnConsulta.setOnClickListener {
            startActivity(Intent(this,ScreenConsulta::class.java))
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun check_Connectivity():Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))
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

    private fun enviar_datos(){
        val date = binding.editFecha.text.toString()
        val hora = binding.editHora.text.toString()
        val dni = binding.editDni.text.toString()
        val procedencia = binding.editProcedencia.text.toString()
        val n_habitacion = binding.editHabitaciones.text.toString()
        val precio_h = binding.editPrecio.text.toString()
        val ayn = binding.editApellidos.text.toString()
        //------------------------------------------
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Precaucion")

        if(date.isEmpty() || hora.isEmpty()){
            alert.setMessage("No ha ingresado la fecha o hora, completar")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.show()
        }else if (dni.length in 1..7){
            alert.setMessage("La cantidad de numeros del DNI ingresado es menor a 8 numeros,¿Desea registrarlo de todas formas?")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.setNeutralButton("Ingresar de todas formas"){btnN, _ ->
                VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos())
                btnN.cancel()
            }
            alert.show()
        }

        else if(procedencia.isEmpty()){
            alert.setMessage("No ha ingresado la procedencia, completar")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.show()
        }

        else if(n_habitacion.isEmpty()){
            alert.setMessage("No ha ingresado la procedencia, completar")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.show()
        }
        else if(precio_h.isEmpty()){
            alert.setMessage("No ha ingresado el precio de la habitacion, completar")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.show()
        }
        else if(ayn.isEmpty()){
            alert.setMessage("No ha ingresado el nombre y apellido del cliente, completar")
            alert.setCancelable(true)
            alert.setPositiveButton("OK"){ btn, _ ->
                btn.cancel()
            }
            alert.show()
        }
        //-----------------------------------------
        else{
            val Loading = DialogProgressShow(this)
            Loading.starLoading()
            VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos())
            Handler(Looper.getMainLooper()).postDelayed({
                Loading.closeLoading()
            },4000)
        }
    }

    private fun cliente_datos(): JsonObjectRequest {
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1pc00XXcYE7gKapkw24tRiKf46SdreeZzvb43FEGFlhI&sheet=formulario1"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response)
            }, { Response.ErrorListener { Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    private fun parseObject(response: JSONObject){
        val jArrayFormulario = response.getJSONArray("formulario1")
        val size = jArrayFormulario.length()
        val a = jArrayFormulario.getJSONObject(size - 1)

        val d = clientesDatos(
            a.getString("N°_habitaciones"),
            a.getString("Fecha"),
            a.getString("Hora"),
            a.getString("Apellidos_y_Nombres"),
            a.getString("DNI"),
            a.getString("Precio"),
            a.getString("Procedencia"),
            a.getString("Observaciones"),
            a.getInt("id").toString())

        val date = binding.editFecha.text.toString()
        val Hora = binding.editHora.text.toString()
        var AyN = binding.editApellidos.text.toString()
        val Habitacion = binding.editHabitaciones.text.toString()
        val Dni = binding.editDni.text.toString()
        val Precio = binding.editPrecio.text.toString()
        var Observaciones = binding.editObservaciones.text.toString()
        var Procedencia = binding.editProcedencia.text.toString()
        val Id = (d.id.toInt() + 1).toString()

        Observaciones = Observaciones.replace(" ","+")
        AyN = AyN.replace(" ","+")
        Procedencia = Procedencia.replace(" ","+")

        val datos = arrayListOf<String>()
        datos.addAll(listOf(
            Habitacion,
            date,
            Hora,
            AyN,
            Dni,
            Precio,
            Procedencia,
            Observaciones,
            Id
        ))

        //enviarDatos(datos)
        enviarDatosUrl(this,datos).todosLosDatos()
        val data = DataHotel.prefer.getHabitaciones()
        for(i in 0 until number_hab.size){
            if(number_hab[i] == Habitacion){
                data[i] = true
                break
            }
        }
        DataHotel.prefer.saveHabitaciones(data)
    }

    private fun mostrarHora(){
        val timePicker = datosHora{timeSeleccionado(it)}
        timePicker.show(supportFragmentManager,"timePicker")
    }

    private fun timeSeleccionado(time:String){
        val hora: TextInputEditText = findViewById(R.id.edit_hora)

        val strHora = time.split(":")
        var Time = time
        if(strHora[1].toInt() in 0..9){
            Time = strHora[0] +":0" + strHora[1]
        }
        hora.setText(Time)
    }

    private fun mostrarFecha() {
        //Llamamos al class donde se ejecutara la parte del fragment para el calendario al momento de pulsarlo
        //Colocamos en el mismo orden de las variables que se encuentran en la funcion datosDeFechaHora
        val datePicker = datosDeFecha { day, month, year -> dateSeleccionado(day, month + 1, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }

    //Funcion para obtener los datos que deseamos dia,mes y year
    @SuppressLint("SetTextI18n")
    fun dateSeleccionado(day: Int, month: Int, year:Int){
        val fecha = findViewById<TextInputEditText>(R.id.edit_fecha)
        var d: String
        var m: String
        if(day < 10){
            d = "0$day"
        }else{
            d = "$day"
        }
        if(month < 10){
            m = "0$month"
        }else{
            m = "$month"
        }
        fecha.setText("$d/$m/$year")
    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,MainActivity::class.java))
        return true
    }
}