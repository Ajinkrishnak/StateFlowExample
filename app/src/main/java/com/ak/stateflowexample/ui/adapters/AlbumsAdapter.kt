package com.ak.stateflowexample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ak.stateflowexample.data.model.AlbumsItem
import com.ak.stateflowexample.databinding.AdapterItemBinding

class AlbumsAdapter : ListAdapter<AlbumsItem, AlbumsAdapter.ItemViewHolder>(NftDiffCallBack()) {

    var itemClickListener: ((view: View, item: AlbumsItem, position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val data = getItem(position)

        holder.binding.apply {
            name.text = data.title
            ivPicture.load(data.thumbnailUrl){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                itemClickListener?.invoke(it, data, position)
            }
        }

    }

    inner class ItemViewHolder(
         val binding: AdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class NftDiffCallBack : DiffUtil.ItemCallback<AlbumsItem>() {
        override fun areItemsTheSame(oldItem: AlbumsItem, newItem: AlbumsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumsItem, newItem: AlbumsItem): Boolean {
            return oldItem == newItem
        }
    }

}