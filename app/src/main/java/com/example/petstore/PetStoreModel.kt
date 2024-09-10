package com.example.petstore

import androidx.lifecycle.ViewModel

class PetStoreModel : ViewModel() {
    val availablePets = mutableListOf<Pet>(
        Pet( "\uD83D\uDC36",30),
        Pet("\uD83D\uDC2F", 30),
        Pet("\uD83D\uDC37", 80),
        Pet("\uD83D\uDC1F", 10),
        Pet("\uD83E\uDD86", 20),
        Pet("\uD83D\uDC12", 100)
    )
    private val userPets = mutableListOf<String>()
    var userMoney = 100

    fun buyPet(pet: String): Boolean {
        val myPet = availablePets.first { p ->
            p.pet == pet
        }
        if (userMoney - myPet.price < 0) {
            return false
        }
        decrementUserMoney(myPet.price)
        addUserPet(myPet.pet)
        removePet(myPet.pet)
        return true
    }

    private fun removePet(pet: String) {
        val petIndex = availablePets.indexOfFirst { p ->
            p.pet == pet
        }
        availablePets.removeAt(petIndex)
    }

    private fun decrementUserMoney(amount: Int) {
        userMoney -= amount
    }

    private fun addUserPet(pet: String) {
        userPets.add(pet)
    }
}