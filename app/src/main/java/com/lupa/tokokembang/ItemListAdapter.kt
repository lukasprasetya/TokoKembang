package com.lupa.tokokembang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lupa.tokokembang.database.ItemDB
import com.lupa.tokokembang.databinding.ItemListRowBinding

class ItemListAdapter(private val onItemClicked: (ItemDB) -> Unit) :
    ListAdapter<ItemDB, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListRowBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemDB) {
            binding.tvNamaBarang.text = item.itemName
            binding.tvSupplier.text = item.itemSupplier
            binding.tvDate.text = item.itemDate
            binding.tvQty.text = item.itemQuantity.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ItemDB>() {
            override fun areItemsTheSame(oldItem: ItemDB, newItem: ItemDB): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ItemDB, newItem: ItemDB): Boolean {
                return oldItem.itemName == newItem.itemName
            }
        }
    }
}
