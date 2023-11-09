package com.kubernights.tp3.parcialnw.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kubernights.tp3.parcialnw.databinding.ItemFragmentMascotaBinding
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.ui.holder.PetHolder
import com.kubernights.tp3.parcialnw.ui.view.HomeFragmentDirections

class PetAdoptableAdapter(private var dogs: MutableList<Dog>) : RecyclerView.Adapter<PetHolder>() {

    private var userBookmarks = mutableListOf<String>()

    fun updateUserBookmarks(bookmarks: List<String>) {
        userBookmarks.clear()
        userBookmarks.addAll(bookmarks)
        notifyDataSetChanged() // Refresh the RecyclerView with the new bookmarks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHolder {
        val binding = ItemFragmentMascotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetHolder(binding)
    }

    override fun onBindViewHolder(holder: PetHolder, position: Int) {
        val dog = dogs[position]
        holder.toggleButton.isChecked = userBookmarks.contains(dog.id)

        holder.getCardLayout().setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedPet = dogs[position]
                Log.d("PetAdoptableFirestoreAdapter", "onBindViewHolder: " + clickedPet.petName)
                Toast.makeText(it.context, "Pet name: ${clickedPet.petName}", Toast.LENGTH_SHORT).show()
            // Handle navigation with the appropriate action, passing the necessary data
                // val action = HomeFragmentDirections.actionGlobalToPetInAdoptionDetailFragment(clickedPet, userId)
                // it.findNavController().navigate(action)
            }
        }

        // Assuming the Dog domain model has all the properties needed for binding
        holder.setCard(
            petName = dog.petName,
            petAge = dog.petAge,
            petGender = dog.petGender,
            petImg = dog.imageUrls,
            petBreed = dog.petBreed,
            petSubBreed = dog.petSubBreed
        )
    }

    override fun getItemCount(): Int = dogs.size

    fun updateData(newData: List<Dog>) {
        this.dogs.clear()
        this.dogs.addAll(newData)
        notifyDataSetChanged() // This notifies the adapter to refresh the view
    }
}
