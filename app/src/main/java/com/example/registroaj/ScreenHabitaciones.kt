package com.example.registroaj

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.registroaj.databinding.ActivityScreenHabitacionesBinding
import com.example.registroaj.dialogoption.MyDialogSetting
import com.example.registroaj.preferencesHabitacion.DataHotel

class ScreenHabitaciones : AppCompatActivity() {
    private lateinit var binding: ActivityScreenHabitacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenHabitacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para el toolbar de regreso
        val back_menu = supportActionBar
        back_menu!!.title = "Regresar"
        back_menu.subtitle = "Habitaciones Disponibles"
        back_menu.setDisplayHomeAsUpEnabled(true)
        back_menu.setHomeAsUpIndicator(R.drawable.back_menu)

        var stateButtons = readValue()
        var txtButtons = readTxt()

        val ImagenButton = listOf(
            binding.h101,
            binding.h102,
            binding.h103,
            binding.h104,
            binding.h105,
            binding.h106,
            binding.h107,
            binding.h108,
            binding.h201,
            binding.h202,
            binding.h203,
            binding.h204,
            binding.h205,
            binding.h206,
            binding.h207)

        val txtHabitaciones = listOf(
            binding.L101,
            binding.L102,
            binding.L103,
            binding.L104,
            binding.L105,
            binding.L106,
            binding.L107,
            binding.L108,
            binding.L201,
            binding.L202,
            binding.L203,
            binding.L204,
            binding.L205,
            binding.L206,
            binding.L207,
        )

        val txtPrecio = listOf(
            binding.h101S,
            binding.h102S,
            binding.h103S,
            binding.h104S,
            binding.h105S,
            binding.h106S,
            binding.h107S,
            binding.h108S,
            binding.h201S,
            binding.h202S,
            binding.h203S,
            binding.h204S,
            binding.h205S,
            binding.h206S,
            binding.h207S,
        )

        //Read and have the state of the buttons
        for(i in 0 until stateButtons.size){
            if(stateButtons[i]){
                ImagenButton[i].setImageResource(R.drawable.habitacion_off)
                txtHabitaciones[i].setText(R.string.HO)
            }else{
                ImagenButton[i].setImageResource(R.drawable.habitacion_on)
                txtHabitaciones[i].setText(R.string.Hd)
            }
        }

        //Read abd have the txt of price of the buttons
        for(i in 0 until txtButtons.size){
            txtPrecio[i].setText(txtButtons[i])
        }

        ImagenButton[0].setOnClickListener{stateChange(stateButtons,0,ImagenButton[0],txtHabitaciones[0])}
        ImagenButton[1].setOnClickListener{stateChange(stateButtons,1,ImagenButton[1],txtHabitaciones[1])}
        ImagenButton[2].setOnClickListener{stateChange(stateButtons,2,ImagenButton[2],txtHabitaciones[2])}
        ImagenButton[3].setOnClickListener{stateChange(stateButtons,3,ImagenButton[3],txtHabitaciones[3])}
        ImagenButton[4].setOnClickListener{stateChange(stateButtons,4,ImagenButton[4],txtHabitaciones[4])}
        ImagenButton[5].setOnClickListener{stateChange(stateButtons,5,ImagenButton[5],txtHabitaciones[5])}
        ImagenButton[6].setOnClickListener{stateChange(stateButtons,6,ImagenButton[6],txtHabitaciones[6])}
        ImagenButton[7].setOnClickListener{stateChange(stateButtons,7,ImagenButton[7],txtHabitaciones[7])}
        ImagenButton[8].setOnClickListener{stateChange(stateButtons,8,ImagenButton[8],txtHabitaciones[8])}
        ImagenButton[9].setOnClickListener{stateChange(stateButtons,9,ImagenButton[9],txtHabitaciones[9])}
        ImagenButton[10].setOnClickListener{stateChange(stateButtons,10,ImagenButton[10],txtHabitaciones[10])}
        ImagenButton[11].setOnClickListener{stateChange(stateButtons,11,ImagenButton[11],txtHabitaciones[11])}
        ImagenButton[12].setOnClickListener{stateChange(stateButtons,12,ImagenButton[12],txtHabitaciones[12])}
        ImagenButton[13].setOnClickListener{stateChange(stateButtons,13,ImagenButton[13],txtHabitaciones[13])}
        ImagenButton[14].setOnClickListener{stateChange(stateButtons,14,ImagenButton[14],txtHabitaciones[14])}


    }

    private fun stateChange(data:ArrayList<Boolean>,index:Int, button:ImageButton, text:TextView){
        data[index] = data[index].not()
        DataHotel.prefer.saveHabitaciones(data)

        if(data[index]){
            button.setImageResource(R.drawable.habitacion_off)
            text.setText(R.string.HO)
        }else{
            button.setImageResource(R.drawable.habitacion_on)
            text.setText(R.string.Hd)
        }
    }

    private fun readValue():ArrayList<Boolean>{
        return DataHotel.prefer.getHabitaciones()
    }

    private fun readTxt():ArrayList<String>{
        return DataHotel.prefer.getTxtHabitaciones()
    }

    //Funcion para retornar al anterior screen de la aplicacion
    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this,MainActivity::class.java))
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.setting -> {
                MyDialogSetting().show(supportFragmentManager,"mydialogsetting")
                return true
            }else -> super.onOptionsItemSelected(item)
        }
    }
}