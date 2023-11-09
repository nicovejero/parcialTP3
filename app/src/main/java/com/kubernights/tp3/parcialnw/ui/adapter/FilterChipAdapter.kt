package com.kubernights.tp3.parcialnw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kubernights.tp3.parcialnw.databinding.ItemFilterChipBinding

class FilterChipAdapter(
    private var chips: List<String>,
    private val onChipClicked: (breed: String) -> Unit
) : RecyclerView.Adapter<FilterChipAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onChipClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breed = chips[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = chips.size

    fun updateChips(newChips: List<String>) {
        this.chips = newChips
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemFilterChipBinding,
        private val onChipClicked: (breed: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: String) {
            (binding.filterChip).apply {
                text = breed
                setOnClickListener { onChipClicked(breed) }
            }
        }
    }
}
