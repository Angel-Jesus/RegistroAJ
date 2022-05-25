package com.example.registroaj.productos.recycleviewP

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.productos.productosDatos.ProductosVenta

class SellAdapter(private val sell_list:ArrayList<ProductosVenta>, private val onClickListener: () -> Unit):RecyclerView.Adapter<SellViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SellViewHolder(layoutInflater.inflate(R.layout.item_sell_product,parent,false))
    }

    override fun onBindViewHolder(holder: SellViewHolder, position: Int) {
        val item = sell_list[position]
        holder.render(item, position, onClickListener)
    }

    override fun getItemCount(): Int = sell_list.size
}