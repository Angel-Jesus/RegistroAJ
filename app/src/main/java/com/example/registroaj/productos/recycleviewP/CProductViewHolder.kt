package com.example.registroaj.productos.recycleviewP

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.databinding.ItemTableProductBinding
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.productosDatos.ProductosDatos

class CProductViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemTableProductBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(ProductosLista: ProductosDatos, pos:Int, onClickListener:(ArrayList<String>) -> Unit){
        binding.idProducto.text = ProductosLista.name_product
        binding.idPrecio.text = "S/. ${ProductosLista.price_product}"
        binding.idStock.text = ProductosLista.stock_product

        binding.idProducto.setOnClickListener {
            onClickListener(arrayListOf("delete",pos.toString(),""))
        }

        binding.idPrecio.setOnClickListener {
            onClickListener(arrayListOf("Change",pos.toString(),"c"))
        }

        binding.idStock.setOnClickListener {
            onClickListener(arrayListOf("Change",pos.toString(),"s"))
        }
    }

}