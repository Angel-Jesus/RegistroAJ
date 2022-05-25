package com.example.registroaj.productos.recycleviewP

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.productos.productosDatos.ProductosListVenta

class ProductosListVAdapter(private val listVenta: ArrayList<ProductosListVenta>):RecyclerView.Adapter<ProductoListVViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoListVViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoListVViewHolder(layoutInflater.inflate(R.layout.item_productlist,parent,false))
    }

    override fun onBindViewHolder(holder: ProductoListVViewHolder, position: Int) {
        val item = listVenta[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listVenta.size
}