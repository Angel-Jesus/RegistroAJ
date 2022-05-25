package com.example.registroaj.estadistica.adaptadorE

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.databinding.ItemLugarBinding
import com.example.registroaj.estadistica.Procedencia

class EstadisticaViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemLugarBinding.bind(view)
    fun render(Lugar: Procedencia){
        binding.idRegion.text = Lugar.procedencia
        binding.idCantidadRegion.text = Lugar.cantidad.toString()
    }
}