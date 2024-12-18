package com.aula.sqlitetest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseHandler (ctx: Context): SQLiteOpenHelper(ctx,DB_NAME,null,DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addDato(dato: Dato): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, dato.nombre)
        val _success = db.insert(TABLE_NAME,null,values)
        return (("$_success").toInt() != -1)
    }

    fun getDato(_id: Int): Dato {
        val Dato = Dato()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        Dato.id = cursor.getInt(cursor.getColumnIndex(ID))
        Dato.nombre = cursor.getString(cursor.getColumnIndex(NAME))
        cursor.close()
        return Dato
    }

    fun dato(): ArrayList<Dato> {
        val DatoList = ArrayList<Dato>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val Dato = Dato()
                    Dato.id = cursor.getInt(cursor.getColumnIndex(ID))
                    Dato.nombre = cursor.getString(cursor.getColumnIndex(NAME))
                    DatoList.add(Dato)
                }while(cursor.moveToNext())
            }
        }
        cursor.close()
        return DatoList
    }

    fun updateDato(Dato: Dato): Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAME, Dato.nombre)
        }
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(Dato.id.toString())).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun deleteDato(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAllDato(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Test"
        private val TABLE_NAME = "datos"
        private val ID = "Id"
        private val NAME = "Nombre"
    }
}