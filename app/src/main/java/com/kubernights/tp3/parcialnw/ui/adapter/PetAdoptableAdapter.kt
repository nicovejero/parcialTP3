package com.kubernights.tp3.parcialnw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubernights.tp3.parcialnw.databinding.ItemFragmentMascotaBinding
import com.kubernights.tp3.parcialnw.domain.model.Dog

class PetAdoptableAdapter(private var dogs: List<Dog>) : RecyclerView.Adapter<PetAdoptableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFragmentMascotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogs[position]
        holder.bind(dog)
    }

    override fun getItemCount(): Int = dogs.size

    fun updateData(newDogs: List<Dog>) {
        this.dogs = newDogs
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemFragmentMascotaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            binding.tvCardNombre.text = dog.petName
            binding.tvCardRaza.text = dog.petBreed
            binding.tvCardSubRaza.text = dog.petSubBreed
            binding.tvCardEdad.text = dog.petAge.toString()
            binding.tvCardGenero.text = dog.petGender.toString()
        }
    }
}
