package com.example.registroaj.productos.recycleviewP

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.productos.productosDatos.ProductosDatos

class ProductAdapter(private val productosList: ArrayList<ProductosDatos>, private val onClickListener:(ArrayList<String>) -> Unit): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_products, parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productosList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = productosList.size
}