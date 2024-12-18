package com.aula.sqlitetest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Iniciando a RecyclerView
    var listaAdapter: ListaAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    // SQLite
    var List = ArrayList<Dato>()
    var databaseHandler = DatabaseHandler(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        btnInsert.setOnClickListener {
            val intent = Intent(this, NameActivity::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView(){
        List = databaseHandler.dato()
        listaAdapter = ListaAdapter(List,this, this::deleteAdapter)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = listaAdapter
    }
    private fun deleteAdapter(position: Int){
        List.removeAt(position)
        listaAdapter!!.notifyItemRemoved(position)
    }
}