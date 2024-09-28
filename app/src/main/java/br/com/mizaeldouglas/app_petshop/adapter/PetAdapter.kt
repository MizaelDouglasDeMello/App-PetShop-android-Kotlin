package br.com.mizaeldouglas.app_petshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mizaeldouglas.app_petshop.databinding.ItemPetBinding
import br.com.mizaeldouglas.app_petshop.model.Pet

class PetAdapter(private var petList: MutableList<Pet>): RecyclerView.Adapter<PetAdapter.PetViewHolder>() {
    private var buttonsVisibility: Int = View.VISIBLE

    class PetViewHolder(val binding: ItemPetBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun getItemCount() = petList.size

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentPet = petList[position]
        with(holder.binding) {
            petName.text = currentPet.name
            petBreed.text = currentPet.breed
            txtTypeAnimal.text = currentPet.type
            btnDelete.visibility = buttonsVisibility
            btnUpdate.visibility = buttonsVisibility
        }
    }

    fun updatePetList(newPetList: MutableList<Pet>) {
        petList = newPetList
        notifyDataSetChanged()
    }

    fun setButtonsVisibility(visibility: Int) {
        buttonsVisibility = visibility
        notifyDataSetChanged()
    }
}