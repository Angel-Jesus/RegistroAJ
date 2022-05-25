package com.example.registroaj

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.registroaj.databinding.ActivityScreenSellProductBinding
import com.example.registroaj.dialogoption.DialogProgressShow
import com.example.registroaj.funcioneshttp.VolleySingleton
import com.example.registroaj.funcioneshttp.enviarVentaUrl
import com.example.registroaj.internetconection.CheckNetworkConnection
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.recycleviewP.SellAdapter
import org.json.JSONObject
import java.util.*

class ScreenSellProduct : AppCompatActivity() {

    private lateinit var binding: ActivityScreenSellProductBinding
    private lateinit var checkNetworkConnection: CheckNetworkConnection

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenSellProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        action_Bar()
        val data = DataHotel.venta.getVenta()

        binding.btnGuardarVenta.isEnabled = check_Connectivity()

        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this, { isConnected ->
            if(isConnected.not()){
                show_alert_Connection()
                binding.btnGuardarVenta.isEnabled = false
            }else{
                if(data[0].n_product == ""){
                    binding.totalV.text = "0"
                    binding.btnGuardarVenta.isEnabled = false
                }else{
                    binding.btnGuardarVenta.isEnabled = true
                    showRecycle()
                    onClickListener()
                }
            }
        })

        binding.btnGuardarVenta.setOnClickListener { val Loading = DialogProgressShow(this)
            Loading.starLoading()
            VolleySingleton.getInstance(this).addToRequestQueue(getDataVenta())
            Handler(Looper.getMainLooper()).postDelayed({
                Loading.closeLoading()
            },3000)}

        binding.limpiarRegistro.setOnClickListener {ClearData()}

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun check_Connectivity():Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
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

    private fun getDataVenta(): JsonObjectRequest {
        val url = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1AiXz1H-ZHdDEqVZ4Way4DiJoWFOcMfovYD775_EAH6k&sheet=formularioventa"
        val request = JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                parseObject(response)
            }, { Response.ErrorListener { Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()}})
        return request
    }

    private fun parseObject(response: JSONObject) {
        //--Variables del Json Ventas-----------------------------------
        val jArrayVenta = response.getJSONArray("formularioventa")
        val size = jArrayVenta.length()
        val a = jArrayVenta.getJSONObject(size - 1)
        //-------------------------------------------------
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val m = calendar.get(Calendar.MONTH) + 1
        val day:String = if(d < 10){ "0$d" }else{ d.toString() }
        val month:String = if(m < 10){ "0$m"}else{ m.toString() }
        val fecha = "$day/$month/$year"
        val id = a.getString("id").toInt() + 1
        val data = DataHotel.venta.getVenta()

        enviarVentaUrl(this,data,fecha,id).ventaSend()
        ClearData()
    }

    private fun ClearData(){
        DataHotel.venta.clearData()
        startActivity(Intent(this,ScreenSellProduct::class.java))
    }

    private fun showRecycle() {
        val list_venta = DataHotel.venta.getVenta()
        binding.recycleSell.layoutManager = LinearLayoutManager(this)
        binding.recycleSell.adapter = SellAdapter(list_venta){onClickListener()}
    }

    @SuppressLint("SetTextI18n")
    private fun onClickListener(){
        val data = DataHotel.venta.getVenta()
        var total = 0.0F

        for(i in data.indices){
            total += (data[i].p_product.toFloat()) * (data[i].c_product.toInt())
        }
        binding.totalV.text = "S/. $total"
    }

    private fun action_Bar() {
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Registro de Venta"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,ScreenProductos::class.java))
        return true
    }
}