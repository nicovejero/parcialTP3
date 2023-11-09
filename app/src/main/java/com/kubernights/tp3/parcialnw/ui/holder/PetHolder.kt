package com.kubernights.tp3.parcialnw.ui.holder

import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.databinding.ItemFragmentMascotaBinding

class PetHolder(private val binding: ItemFragmentMascotaBinding) : RecyclerView.ViewHolder(binding.root) {

    val toggleButton: ToggleButton = binding.toggleBookmark

    fun setCard(petName: String, petAge: Int, petGender: String, petImg: List<String>, petBreed: String, petSubBreed: String?) {
        binding.tvCardNombre.text = petName
        binding.tvCardEdad.text = petAge.toString()
        binding.tvCardGenero.text = petGender
        binding.tvCardRaza.text = petBreed
        binding.tvCardSubRaza.text = petSubBreed ?: "" // Assuming petSubBreed can be null and you want to set an empty string in that case

        Glide.with(itemView)
            .load(petImg[0])
            .into(binding.ivCardBackGround)
    }

    fun getCardLayout() = binding.cardAdoption
}
