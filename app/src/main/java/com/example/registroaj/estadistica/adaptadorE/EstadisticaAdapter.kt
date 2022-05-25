package com.example.registroaj.estadistica.adaptadorE

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.R
import com.example.registroaj.estadistica.Procedencia

class EstadisticaAdapter(private val LugarList: ArrayList<Procedencia>):RecyclerView.Adapter<EstadisticaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadisticaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EstadisticaViewHolder(layoutInflater.inflate(R.layout.item_lugar,parent,false))
    }

    override fun onBindViewHolder(holder: EstadisticaViewHolder, position: Int) {
        val item = LugarList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = LugarList.size
}