package com.example.registroaj.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.clientesDatos

class ClientesAdapter(private val clientesList: ArrayList<clientesDatos>, private val onClickListener:(clientesDatos) -> Unit): RecyclerView.Adapter<CliendesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CliendesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CliendesViewHolder(layoutInflater.inflate(R.layout.item_clientes,parent,false))
    }

    override fun onBindViewHolder(holder: CliendesViewHolder, position: Int) {
        val intem = clientesList[position]
        holder.render(intem, onClickListener)
    }

    override fun getItemCount(): Int = clientesList.size
}