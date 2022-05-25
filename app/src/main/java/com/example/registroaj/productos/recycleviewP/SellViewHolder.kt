package com.example.registroaj.productos.recycleviewP

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.registroaj.databinding.ItemSellProductBinding
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.example.registroaj.productos.productosDatos.ProductosVenta

class SellViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSellProductBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(list:ProductosVenta, posicion:Int, onClickListener:() -> Unit){
        binding.P.text = list.n_product
        binding.price.text = "Precio: S/.${list.p_product}"
        binding.total.text = list.c_product

        binding.Mas.setOnClickListener {
            binding.total.text = "${binding.total.text.toString().toInt() + 1}"
            DataHotel.venta.changeData(binding.total.text.toString(),posicion)
            onClickListener()
        }
        binding.Menos.setOnClickListener {
            val menos = if(binding.total.text.toString().toInt() <= 0){ 0 }else{binding.total.text.toString().toInt() - 1}
            binding.total.text = menos.toString()
            DataHotel.venta.changeData(binding.total.text.toString(),posicion)
            onClickListener()
        }
    }
}