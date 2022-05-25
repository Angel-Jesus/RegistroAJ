package com.example.registroaj.funcioneshttp

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class enviarDatosUrl(val context: Context,cliente:ArrayList<String>) {

    val urlTodo = "https://docs.google.com/forms/d/e/1FAIpQLSf00TdVUZYuJtj8gMJf1NfNmFDJnnY3SP4unjaijeHKFOygPg/formResponse?usp=pp_url&entry.1355511849='${cliente[0]}&entry.1239323002=${cliente[1]}&entry.310648117=${cliente[2]}&entry.1438208562=${cliente[3]}&entry.397742594='${cliente[4]}&entry.1625760868='${cliente[5]}&entry.146480289=${cliente[6]}&entry.703144027=${cliente[7]}&entry.240160237=POST&entry.368901014=${cliente[8]}"
    val urlDelete = "https://docs.google.com/forms/d/e/1FAIpQLSf00TdVUZYuJtj8gMJf1NfNmFDJnnY3SP4unjaijeHKFOygPg/formResponse?usp=pp_url&entry.240160237=DELETE&entry.368901014=${cliente[8]}"
    val urlUpdate = "https://docs.google.com/forms/d/e/1FAIpQLSf00TdVUZYuJtj8gMJf1NfNmFDJnnY3SP4unjaijeHKFOygPg/formResponse?usp=pp_url&entry.1355511849='${cliente[0]}&entry.1239323002=${cliente[1]}&entry.310648117=${cliente[2]}&entry.1438208562=${cliente[3]}&entry.397742594='${cliente[4]}&entry.1625760868='${cliente[5]}&entry.146480289=${cliente[6]}&entry.703144027=${cliente[7]}&entry.240160237=UPDATE&entry.368901014=${cliente[8]}"

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
                        "G" -> Toast.makeText(context, "Registro Guardado", Toast.LENGTH_SHORT).show()
                        "E" -> Toast.makeText(context,"Registro Eliminado", Toast.LENGTH_SHORT).show()
                        "A" -> Toast.makeText(context,"Registro Actualizado", Toast.LENGTH_SHORT).show()
                    }
                },
                { Toast.makeText(context,"Ocurrio un problema", Toast.LENGTH_SHORT).show()})

            // Add the request to the RequestQueue.
            queue.add(stringRequest)

    }
}