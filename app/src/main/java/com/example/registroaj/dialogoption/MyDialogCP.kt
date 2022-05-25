package com.example.registroaj.dialogoption

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.registroaj.R
import com.example.registroaj.preferencesHabitacion.DataHotel
import com.google.android.material.textfield.TextInputEditText
import java.lang.IllegalStateException

class MyDialogCP(private val d: ArrayList<String>):DialogFragment(){

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.view_cantidad_product,null))

            alertDialog.setPositiveButton("Agregar a caja"){btn,_ ->
                val datos = DataHotel.venta.getVenta()
                println(datos)
                var state = true
                var exceso = false
                val txtCantidad = dialog?.findViewById<TextInputEditText>(R.id.edit_cantidad)
                val cantidad = txtCantidad!!.text.toString()

                if(cantidad != ""){
                    //Comprobar si la cantidad excede al stock del producto
                    val stock = DataHotel.prod.getProduct()
                    for(i in stock.indices){
                        if(stock[i].name_product == d[0] && stock[i].stock_product.toInt() >= cantidad.toInt()){
                            exceso = true
                            break
                        }
                    }
                    //--------------------------------------------------
                    if(exceso){
                        for(i in datos.indices){
                            if(datos[i].n_product == d[0]){
                                state = false
                                break
                            }
                        }
                        if(state){
                            DataHotel.venta.sendProducts(d[0],d[1],cantidad)
                            Toast.makeText(context,"Añadido con exito", Toast.LENGTH_SHORT).show()
                            println(DataHotel.venta.getVenta())
                            btn.cancel()
                        }else{
                            Toast.makeText(context,"El producto ya se añadio a registro de venta", Toast.LENGTH_SHORT).show()
                            btn.cancel()
                        }
                    }else{
                        Toast.makeText(context,"Excede la cantidad que existe en stock", Toast.LENGTH_SHORT).show()
                        btn.cancel()
                    }
                }else{
                    Toast.makeText(context,"No coloco la cantidad", Toast.LENGTH_SHORT).show()
                    btn.cancel()
                }

            }
            alertDialog.setNegativeButton("Cancelar"){btn,_ -> btn.cancel()}
            alertDialog.create()
        }?:throw IllegalStateException("Activity is null")
    }

}