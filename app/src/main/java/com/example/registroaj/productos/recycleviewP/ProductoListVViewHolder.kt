package com.example.registroaj.productos.recycleviewP

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.databinding.ItemProductlistBinding
import com.example.registroaj.productos.productosDatos.ProductosListVenta

class ProductoListVViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemProductlistBinding.bind(view)

    fun render(productList:ProductosListVenta){
        binding.fechaV.text = productList.Fecha
        binding.cantidadV.text = productList.Cantidad
        binding.precioV.text = productList.Precio
        binding.productosV.text = productList.Producto
    }
}