package com.example.petstore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PetStoreState(
    val usersMoney: Int = 100,
    val myPets: String = "My pets: ",
    val availablePets: MutableList<Pet> = mutableListOf<Pet>(
        Pet( "\uD83D\uDC36",30),
        Pet("\uD83D\uDC2F", 30),
        Pet("\uD83D\uDC37", 80),
        Pet("\uD83D\uDC1F", 10),
        Pet("\uD83E\uDD86", 20),
        Pet("\uD83D\uDC12", 100)
    )
)

class PetStoreModel : ViewModel() {
    private val _state = MutableStateFlow(PetStoreState())
    val uiState: StateFlow<PetStoreState> = _state.asStateFlow()

    fun buyPet(petStr: String): Boolean {
        var wasBought = true
        _state.update { currentState ->
            val pet = currentState.availablePets.first {
                p ->
                p.pet == petStr
            }
            if (currentState.usersMoney - pet.price < 0) {
                wasBought = false
                currentState
            } else {
                currentState.copy(
                    usersMoney = currentState.usersMoney - pet.price,
                    myPets =  currentState.myPets + pet.pet,
                    availablePets = currentState.availablePets.filter { p ->
                        p.pet != petStr
                    }.toMutableList()

                )
            }

        }
        return wasBought
    }
}