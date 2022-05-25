package com.example.registroaj.productos.recycleviewP

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.databinding.ItemProductsBinding
import com.example.registroaj.productos.productosDatos.ProductosDatos

class ProductViewHolder(view:View): RecyclerView.ViewHolder(view) {
    private val binding = ItemProductsBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(ProductosLista:ProductosDatos, onClickListener:(ArrayList<String>) -> Unit){
        binding.typeProduct.text = ProductosLista.name_product
        binding.price.text = "Precio: S/.${ProductosLista.price_product}"
        binding.stock.text = "Stock Disponible: ${ProductosLista.stock_product}"

        itemView.setOnClickListener {
            val n = binding.typeProduct.text.toString()
            val c = ProductosLista.price_product
            onClickListener(arrayListOf(n,c))
        }
    }
}