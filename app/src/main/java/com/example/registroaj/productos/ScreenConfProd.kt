package com.example.registroaj.productos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registroaj.R
import com.example.registroaj.ScreenProductos
import com.example.registroaj.databinding.ActivityScreenConfProdBinding
import com.example.registroaj.dialogoption.MyDialogChange
import com.example.registroaj.dialogoption.MyDialogOptionAdd
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.productosDatos.ProductosDatos
import com.example.registroaj.productos.recycleviewP.CProducAdapter

class ScreenConfProd : AppCompatActivity() {
    private lateinit var binding: ActivityScreenConfProdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenConfProdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = DataHotel.prod.getProduct()
        actionBar()
        recyclerView(data)

    }

    private fun recyclerView(d:ArrayList<ProductosDatos>) {
        binding.recyclerConfProduc.layoutManager = LinearLayoutManager(this)
        binding.recyclerConfProduc.adapter = CProducAdapter(d){onClickListener(it)}
    }

    private fun onClickListener(filtro: ArrayList<String>) {
        //filtro = [accion a realizar, posicion, caracter de accion en Prod]
        when(filtro[0]){
            "Change" -> {MyDialogChange(filtro[1].toInt(),filtro[2]).show(supportFragmentManager,"ChangeData")}
            else -> {
                ShowDialog(filtro[1].toInt())
            }
        }
    }

    private fun actionBar(){
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Productos"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)
    }

    private fun ShowDialog(posicion:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Producto")
        builder.setMessage("¿Desea eliminar el producto de la lista?")
        builder.setPositiveButton("Eliminar"){btn,_ ->
            DataHotel.prod.deleteProduc(posicion)
            btn.cancel()
            startActivity(Intent(this,ScreenConfProd::class.java))
        }
        builder.setNeutralButton("Cancelar"){btn,_ ->
            btn.cancel()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_add_product,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addProduct ->{
                //Mostrar Dialog para añadir un nuevo producto
                MyDialogOptionAdd().show(supportFragmentManager,"AddProducto")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //Regresar a la ventana anterior
        startActivity(Intent(this,ScreenProductos::class.java))
        return true
    }
}