package com.example.petstore

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.text = "\uD83D\uDC36 Pet Store \uD83D\uDC2F"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        val petModel: PetStoreModel = ViewModelProvider(this).get(PetStoreModel::class.java)
        val updateUi = {
            binding.myPets.text = petModel.uiState.value.myPets
            binding.userBalance.text = "\uD83D\uDCB0 Your remaining money: ¥" + petModel.uiState.value.usersMoney.toString()
        }
        updateUi()
        val setNotEnoughMoneyAlert = { p: Pet ->
            val petName = p.pet
            val petPrice = p.price
            val userMoney = petModel.uiState.value.usersMoney
            alertDialogBuilder.setMessage("You do not have enough money to buy $petName. You have ¥$userMoney and $petName costs ¥$petPrice")
            alertDialogBuilder.show()
        }
        val petAdapter = PetAdapter(
            petModel,
            updateUi,
            setNotEnoughMoneyAlert,
        )
        binding.getMoneyBtn.setOnClickListener {
            petModel.getMoney()
            updateUi()
        }
        binding.petShopPets.adapter = petAdapter
        binding.petShopPets.layoutManager = LinearLayoutManager(this)
    }
}