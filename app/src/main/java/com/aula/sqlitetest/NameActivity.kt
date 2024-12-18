package com.aula.sqlitetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_name.*

class NameActivity : AppCompatActivity() {

    // Database
    val databaseHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        val edit = intent.getBooleanExtra("edit", false)
        val position = intent.getIntExtra("position", 0)
        if(edit){
            val dato = databaseHandler.getDato(position)
            etNome.setText(dato.nombre)
            btnInsertNome.setText("Editar")
        }
        btnInsertNome.setOnClickListener {
            if(etNome.text.toString() == ""){
                Toast.makeText(this,"El Nombre no puede estar vacio.",Toast.LENGTH_SHORT).show()
            }
            else {
                if(edit){
                    val dato = Dato(position, etNome.text.toString())
                    databaseHandler.updateDato(dato)
                    finish()
                }
                else {
                    val dato = Dato(0, etNome.text.toString())
                    databaseHandler.addDato(dato)
                    finish()
                }
            }
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }
}