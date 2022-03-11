package com.example.registroaj

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.databinding.ActivityScreenRegistroBinding
import com.example.registroaj.datospicker.datosDeFecha
import com.example.registroaj.datospicker.datosHora
import com.example.registroaj.funcioneshttp.VolleySingleton
import com.example.registroaj.funcioneshttp.enviarDatosUrl
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class ScreenRegistro : AppCompatActivity() {
    private lateinit var binding: ActivityScreenRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityScreenRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para el toolbar de regreso
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)

        //Funcion para obtener los datos JSON de la base de datos de google sheet

        //Para escoger la hora y fecha en el registro
        binding.editFecha.setOnClickListener {mostrarFecha()}
        binding.editHora.setOnClickListener {mostrarHora()}

        //Funcion para guadar los datos
        binding.btnGuardar.setOnClickListener {
            val date = binding.editFecha.text.toString()
            val hora = binding.editHora.text.toString()
            val dni = binding.editDni.text.toString()
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Precaucion")

            if(date == "" || hora == ""){
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
                    VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos(this))
                    btnN.cancel()
                }
                alert.show()
            }
            else{
                VolleySingleton.getInstance(this).addToRequestQueue(cliente_datos(this))
            }
        }

        //Funcion para ir a la ventana consular
        binding.btnConsulta.setOnClickListener {
            startActivity(Intent(this,ScreenConsulta::class.java))
        }

    }

    private fun cliente_datos(context: Context): JsonObjectRequest {
        val url = "URL POST de google sheet"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response)
            }, { Response.ErrorListener { Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()}})
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
        val progress = binding.cargando
        binding.cargando.isVisible = true
        enviarDatosUrl(this,datos, progress).todosLosDatos()

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
        onBackPressed()
        return true
    }
}