package br.com.mizaeldouglas.app_petshop.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.mizaeldouglas.app_petshop.R
import br.com.mizaeldouglas.app_petshop.database.PetDatabaseHelper
import br.com.mizaeldouglas.app_petshop.databinding.ActivityFormAddPetBinding
import br.com.mizaeldouglas.app_petshop.utils.enums.TypeAnimals

class FormAddPetActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormAddPetBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding){
            btnSend.setOnClickListener{
                send()
            }
        }
        binding.faListPetBtnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val spinner: Spinner = binding.spinnerAnimalType
        ArrayAdapter.createFromResource(
            this,
            R.array.animal_types,
            android.R.layout.simple_spinner_item
        ).also { adapder ->
            adapder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapder
        }


    }

    private fun send() {
        val name = binding.txtNameAnimal.text.toString()
        val breed = binding.txtBreedAnimal.text.toString()
        val selectedPosition = binding.spinnerAnimalType.selectedItemPosition

        if (selectedPosition == 0) {
            Toast.makeText(
                this@FormAddPetActivity,
                "Por favor, selecione um tipo de animal",
                Toast.LENGTH_SHORT
            ).show()
        }
        val type =  TypeAnimals.values()[selectedPosition -1].name

        val dbHelper = PetDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(PetDatabaseHelper.COLUMN_NAME, name)
            put(PetDatabaseHelper.COLUMN_BREED, breed)
            put(PetDatabaseHelper.COLUMN_TYPE, type)
        }
        val newRowId = db.insert(PetDatabaseHelper.TABLE_NAME, null,values)
        if (newRowId != -1L) {
            Toast.makeText(this, "Animal cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Erro ao cadastrar o animal.", Toast.LENGTH_SHORT).show()
        }
    }
}