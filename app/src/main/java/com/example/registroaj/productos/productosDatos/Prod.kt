package com.example.registroaj.productos.productosDatos

import android.annotation.SuppressLint
import android.content.Context

class Prod(contex:Context) {

    val PRODUCT_KEY = "produckey"
    val PRODUCTO = "producto"
    val COSTO = "costo"
    val STOCK = "stock"
    val setting = contex.getSharedPreferences(PRODUCT_KEY,0)

    @SuppressLint("CommitPrefEdits")
    fun saveNewProduct(typroduct:String, cost:String, stock:String){
        val p = setting.getString(PRODUCTO,"PICKEO SNAX,SHAMPO PANTENE,SHAMPO VARONES,CEPILLO KOLINOS, CREMA DENTAL KOLINOS,CERVEZA,AGUA,PRESERVATIVO,TOALLAS HIGIENICAS X3")
        val c = setting.getString(COSTO,"2,2,2,3,3,5,2,2,1")
        val s = setting.getString(STOCK,"12,12,12,12,12,12,12,12,12")

        setting.edit().putString(PRODUCTO,"$p,$typroduct").apply()
        setting.edit().putString(COSTO,"$c,$cost").apply()
        setting.edit().putString(STOCK,"$s,$stock").apply()
    }

    fun getProduct():ArrayList<ProductosDatos>{
        val producto = setting.getString(PRODUCTO,"PICKEO SNAX,SHAMPO PANTENE,SHAMPO VARONES,CEPILLO KOLINOS,CREMA DENTAL KOLINOS,CERVEZA,AGUA,PRESERVATIVO,TOALLAS HIGIENICAS")
        val costo = setting.getString(COSTO,"2,2,2,3,3,5,2,2,1")
        val stock = setting.getString(STOCK,"12,12,12,12,12,12,12,12,12")

        val product_lis = producto?.split(",")
        val costo_lis = costo?.split(",")
        val stock_lis = stock?.split(",")
        val ProductoData = arrayListOf(ProductosDatos("","",""))
        ProductoData.clear()
        for(i in product_lis!!.indices){
            ProductoData.add(i,ProductosDatos(product_lis[i], costo_lis!![i],stock_lis!![i]))
        }
        return ProductoData
    }

    fun deleteProduc(posicion:Int){
        val ProductosList = getProduct()
        ProductosList.removeAt(posicion)
        changeDataList(ProductosList)
    }

    fun changeData(dato:String, posicion: Int, accion:String){
        val d = getProduct()
        if(accion == "c"){
            d[posicion].price_product = dato
        }else{
            d[posicion].stock_product = dato
        }
        changeDataList(d)
    }

    private fun changeDataList(list: ArrayList<ProductosDatos>){
        var p = ""
        var c = ""
        var s = ""

        for (i in list.indices) {
            if (i == 0) {
                p = list[i].name_product
                c = list[i].price_product
                s = list[i].stock_product
            } else {
                p = "$p,${list[i].name_product}"
                c = "$c,${list[i].price_product}"
                s = "$s,${list[i].stock_product}"
            }
        }
        setting.edit().putString(PRODUCTO,p).apply()
        setting.edit().putString(COSTO,c).apply()
        setting.edit().putString(STOCK,s).apply()
    }

}