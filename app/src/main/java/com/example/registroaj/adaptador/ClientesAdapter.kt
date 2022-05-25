package com.example.registroaj.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.clientesDatos

class ClientesAdapter(clientesList: ArrayList<clientesDatos>, private val onClickListener:(clientesDatos) -> Unit): RecyclerView.Adapter<CliendesViewHolder>() {
    val clientes = clientesList.reversed()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CliendesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CliendesViewHolder(layoutInflater.inflate(R.layout.item_clientes,parent,false))
    }

    override fun onBindViewHolder(holder: CliendesViewHolder, position: Int) {
        val item = clientes[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = clientes.size
}