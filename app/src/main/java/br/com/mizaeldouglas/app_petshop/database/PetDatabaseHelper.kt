package br.com.mizaeldouglas.app_petshop.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.mizaeldouglas.app_petshop.utils.enums.TypeAnimals

class PetDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "petshop.db"
        private const val DATABASE_VERSION = 1 // Incrementar a versão do banco de dados
        const val TABLE_NAME = "pets"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_BREED = "breed"
        const val COLUMN_TYPE = "type"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_BREED TEXT, "
                + "$COLUMN_TYPE TEXT)")
        db?.execSQL(createTable)

        // Inserindo registros de teste
//        db?.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_BREED, $COLUMN_TYPE) VALUES ('Marley', 'Vira-Lata', 'CACHORRO')")
//        db?.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_BREED, $COLUMN_TYPE) VALUES ('Lucky', 'Vira-Lata', 'CACHORRO')")
//        db?.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_BREED, $COLUMN_TYPE) VALUES ('Léia', 'Vira-Lata', 'CACHORRO')")
//        db?.execSQL("INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_BREED, $COLUMN_TYPE) VALUES ('Stella', 'Lhasa apso', 'CACHORRO')")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}