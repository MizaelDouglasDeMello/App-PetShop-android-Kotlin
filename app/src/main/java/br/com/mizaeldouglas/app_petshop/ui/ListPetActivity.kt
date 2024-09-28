package br.com.mizaeldouglas.app_petshop.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.app_petshop.R
import br.com.mizaeldouglas.app_petshop.adapter.PetAdapter
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper.Companion.COLUMN_BREED
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper.Companion.COLUMN_ID
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper.Companion.COLUMN_NAME
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper.Companion.COLUMN_TYPE
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper.Companion.TABLE_NAME
import br.com.mizaeldouglas.app_petshop.databinding.ActivityFormAddPetBinding
import br.com.mizaeldouglas.app_petshop.databinding.ActivityListPetBinding
import br.com.mizaeldouglas.app_petshop.model.Pet

class ListPetActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListPetBinding.inflate(layoutInflater)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var petAdapter: PetAdapter
    private var petList: MutableList<Pet> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rcListPet)
        recyclerView.layoutManager = LinearLayoutManager(this)

        petAdapter = PetAdapter(petList)
        recyclerView.adapter = petAdapter

//        binding.bottomNavigation.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

        loadPets()
    }

    private fun loadPets() {
        Log.d("MainActivity_test", "Static pets added: ${petList.size}")

        val dbHelper = PetDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_BREED, COLUMN_TYPE),
            null,
            null,
            null,
            null,
            null
        )

        Log.d("MainActivity_test", "Cursor count: ${cursor.count}")

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val breed = getString(getColumnIndexOrThrow(COLUMN_BREED))
                val type = getString(getColumnIndexOrThrow(COLUMN_TYPE))

                Log.d("MainActivity_test", "Pet from DB - ID: $id, Name: $name, Breed: $breed")

                petList.add(Pet(id, name, breed,type))
            }
        }
        cursor.close()
        Log.d("MainActivity_test", "Total pets after DB load: ${petList.size}")
        petAdapter.updatePetList(petList)
    }
}
