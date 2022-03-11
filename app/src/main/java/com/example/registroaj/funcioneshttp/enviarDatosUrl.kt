package com.example.registroaj.funcioneshttp

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isGone
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class enviarDatosUrl(val context: Context, cliente:ArrayList<String>, private val progress:ProgressBar) {
    /*
    val urlTodo = "Ingrese aqui la url de su base de datos en google sheet"
    val urlDelete = "Ingrese aqui la url de su base de datos en google sheet"
    val urlUpdate = "Ingrese aqui la url de su base de datos en google sheet"
    */
    val urlTodo = "Ingresar el url"
    val urlDelete = "Ingresar el url"
    val urlUpdate = "Ingresar el url"
    fun todosLosDatos(){
        enviarDatos(urlTodo,"G")
    }
    fun eliminar(){
        enviarDatos(urlDelete,"E")
    }

    fun modificar(){
        enviarDatos(urlUpdate,"A")
    }

    private fun enviarDatos(url:String, mensaje:String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                // Display the first 500 characters of the response string.
                when(mensaje){
                    "G" -> {
                        progress.isGone = true
                        Toast.makeText(context, "Registro Guardado", Toast.LENGTH_SHORT).show()
                    }
                    "E" -> Toast.makeText(context,"Registro Eliminado", Toast.LENGTH_SHORT).show()
                    "A" -> Toast.makeText(context,"Registro Actualizado", Toast.LENGTH_SHORT).show()
                }
            },
            { Toast.makeText(context,"Ocurrio un problema", Toast.LENGTH_SHORT).show()})

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}