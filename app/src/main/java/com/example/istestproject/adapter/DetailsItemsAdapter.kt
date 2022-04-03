package com.example.istestproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.istestproject.databinding.DetailsItemLayoutBinding
import com.example.istestproject.model.Item

class DetailsItemsAdapter(
    private val onItemClicked: (Item) -> Unit
) : ListAdapter<Item, DetailsItemsAdapter.ItemViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DetailsItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding, onItemClicked)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bind(currentList[position])
    }


    inner class ItemViewHolder(
        private val productItemBinding: DetailsItemLayoutBinding,
        private val onItemClicked: (Item) -> Unit
    ) : RecyclerView.ViewHolder(productItemBinding.root) {

        private var currentItem: Item? = null


        init {


            productItemBinding.root.setOnClickListener {

                currentItem?.let {
                    onItemClicked.invoke(it)
                }
            }


        }


        fun bind(item: Item) {
            currentItem = item
            currentItem?.let { detailItem ->

                with(productItemBinding)
                {

                    itemPriceTv.text = detailItem.price.toString()
                    itemNameTv.text = detailItem.name
                    itemDescTv.text = detailItem.description


                }


            }

        }


    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id.toLong()
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }


}


