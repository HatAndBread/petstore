package com.example.petstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class PetAdapter(
    private val petsModel: PetStoreModel,
    private val updateBalanceText: (Int) -> Unit,
    private val setNotEnoughMoneyAlert: (Pet) -> AlertDialog,
    private val updateMyPetsText: (String) -> Unit,
    ) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {
    class PetViewHolder (petView: View) : RecyclerView.ViewHolder(petView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.pet, parent, false)

        return PetViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return petsModel.availablePets.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentPet = petsModel.availablePets[position]
        holder.itemView.findViewById<TextView>(R.id.pet_item_name).text = currentPet.pet
        holder.itemView.findViewById<TextView>(R.id.pet_item_price).text = "¥" + currentPet.price.toString()
        holder.itemView.setOnClickListener {
            val petIndex = petsModel.availablePets.indexOfFirst { p -> p.pet == currentPet.pet }
            val bought = petsModel.buyPet(currentPet.pet)
            if (bought) {
                this.notifyItemRemoved(petIndex)
                updateBalanceText(petsModel.userMoney)
                updateMyPetsText(currentPet.pet)
            } else {
                setNotEnoughMoneyAlert(currentPet)
            }
        }
    }
}