package com.example.registroaj.productos.recycleviewP

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.productos.productosDatos.ProductosDatos

class CProducAdapter(private val productlist: ArrayList<ProductosDatos>, private val onClickListener: (ArrayList<String>) -> Unit): RecyclerView.Adapter<CProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CProductViewHolder(layoutInflater.inflate(R.layout.item_table_product, parent, false))
    }

    override fun onBindViewHolder(holder: CProductViewHolder, position: Int) {
        val item = productlist[position]
        holder.render(item, position, onClickListener)
    }

    override fun getItemCount(): Int = productlist.size

}