package com.example.registroaj.funcioneshttp

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.productosDatos.ProductosVenta

class enviarVentaUrl(val context: Context, private val list_venta:ArrayList<ProductosVenta>,private val fecha:String, private var id:Int){
    val product_stock = DataHotel.prod.getProduct()
    fun ventaSend(){
        for(i in list_venta.indices){
            val producto = list_venta[i].n_product.replace(" ","+")
            val precio = list_venta[i].p_product
            val cantidad = list_venta[i].c_product
            val pr = list_venta[i].n_product
            val urlVenta = "https://docs.google.com/forms/d/e/1FAIpQLSeP6LPmpqlMCa2TxQ6Mz2cC7jyAsmAij2QnaToGNP98cywe5Q/formResponse?usp=pp_url&entry.309617657=$fecha&entry.1102938064=$producto&entry.1439643085=$precio&entry.2108873811=$cantidad&entry.766334795=$id"
            enviarVenta(urlVenta,pr, cantidad)
            id += 1
        }
        Toast.makeText(context, "Venta Guardada", Toast.LENGTH_SHORT).show()
    }

    private fun enviarVenta(url_v:String, p:String, c:String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url_v,
            {
                for(i in product_stock.indices){
                    if(product_stock[i].name_product == p){
                        val stock = product_stock[i].stock_product.toInt() - c.toInt()
                        DataHotel.prod.changeData(stock.toString(),i,"")
                        break
                    }
                }
            },
            {Toast.makeText(context,"Ocurrio un problema", Toast.LENGTH_SHORT).show()})
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}