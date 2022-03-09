package com.example.registroaj.adaptador

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.clientesDatos
import com.example.registroaj.databinding.ItemClientesBinding

class CliendesViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemClientesBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(clientesModel: clientesDatos, onClickListener:(clientesDatos) -> Unit) {
        binding.editHabitacion.text = clientesModel.habitacion
        binding.editFecha.text = clientesModel.fecha
        binding.editHora.text = clientesModel.hora
        binding.editApellidos.text = clientesModel.AyN
        binding.editDni.text = clientesModel.dni
        binding.editProcedencia.text = clientesModel.procedencia
        binding.editPrecio.text = "S/. ${clientesModel.precio}"
        binding.editObservacion.text = clientesModel.Observacion

        binding.editHabitacion.setOnClickListener {
            clientesModel.habitacion = "C"
            onClickListener(clientesModel)}

        binding.editFecha.setOnClickListener {
            clientesModel.fecha = "C"
            onClickListener(clientesModel)}

        binding.editHora.setOnClickListener {
            clientesModel.hora = "C"
            onClickListener(clientesModel)}

        binding.editApellidos.setOnClickListener {
            clientesModel.AyN = "C"
            onClickListener(clientesModel)}

        binding.editDni.setOnClickListener {
            clientesModel.dni = "C"
            onClickListener(clientesModel)}

        binding.editProcedencia.setOnClickListener {
            clientesModel.procedencia = "C"
            onClickListener(clientesModel)}

        binding.editPrecio.setOnClickListener {
            clientesModel.precio = "C"
            onClickListener(clientesModel)}

        binding.editObservacion.setOnClickListener {
            clientesModel.Observacion = "C"
            onClickListener(clientesModel)}
        //itemView.setOnClickListener{onClickListener(clientesModel)}
    }
}