package com.example.registroaj.productos.productosDatos

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class Venta(context: Context) {
    private val PRODUCT_KEY = "produckey2"
    private val PRODUCTO = "producto2"
    private val COSTO = "costo2"
    private val CANTIDAD = "cantidad2"
    private val ID = "ID"
    private val CAJA = "caja"

    private val setting = context.getSharedPreferences(PRODUCT_KEY,0)

    @SuppressLint("CommitPrefEdits")
    fun sendProducts(n: String, p:String, c:String){
        //n = nombre del producto, p = precio del porducto, c = cantidad a vender
        val nombre = setting.getString(PRODUCTO,"")
        val precio = setting.getString(COSTO,"")
        val cantidad = setting.getString(CANTIDAD,"")

        if(nombre == ""){ setting.edit().putString(PRODUCTO,n).apply() }else{setting.edit().putString(PRODUCTO,"$nombre,$n").apply()}
        if(precio == ""){ setting.edit().putString(COSTO,p).apply() }else{setting.edit().putString(COSTO,"$precio,$p").apply()}
        if(cantidad == ""){ setting.edit().putString(CANTIDAD,c).apply() }else{setting.edit().putString(CANTIDAD,"$cantidad,$c").apply()}
    }

    fun getVenta():ArrayList<ProductosVenta>{
        val nombre = setting.getString(PRODUCTO,"")
        val precio = setting.getString(COSTO,"")
        val cantidad = setting.getString(CANTIDAD,"")

        val product_lis = nombre?.split(",")
        val costo_lis = precio?.split(",")
        val cantidad_lis = cantidad?.split(",")
        val PVenta = arrayListOf(ProductosVenta("","",""))
        PVenta.clear()

        for(i in product_lis!!.indices){
            PVenta.add(i,ProductosVenta(product_lis[i], costo_lis!![i],cantidad_lis!![i]))
        }
        return PVenta
    }

    fun changeData(total:String, posicion:Int){
        val d = getVenta()
        d[posicion].c_product = total
        changeDataList(d)
    }

    private fun changeDataList(list: ArrayList<ProductosVenta>) {
        var producto = ""
        var precio= ""
        var cantidad = ""

        for (i in list.indices){
            if(i == 0){
                producto = list[i].n_product
                precio = list[i].p_product
                cantidad = list[i].c_product
            }else{
                producto = "$producto,${list[i].n_product}"
                precio = "$precio,${list[i].p_product}"
                cantidad = "$cantidad,${list[i].c_product}"
            }
        }

        setting.edit().putString(PRODUCTO,producto).apply()
        setting.edit().putString(COSTO,precio).apply()
        setting.edit().putString(CANTIDAD,cantidad).apply()
    }

    fun clearData(){
        setting.edit().putString(PRODUCTO,"").apply()
        setting.edit().putString(COSTO,"").apply()
        setting.edit().putString(CANTIDAD,"").apply()
    }

    fun getID(): String? {
        return setting.getString(ID,"0")
    }

    fun changeId(id_new:String){
        setting.edit().putString(ID,id_new).apply()
    }

    fun getCaja(): String? {
        return setting.getString(CAJA, "0")
    }

    fun changeCaja(caja_new:String){
        setting.edit().putString(CAJA,caja_new).apply()
    }

}