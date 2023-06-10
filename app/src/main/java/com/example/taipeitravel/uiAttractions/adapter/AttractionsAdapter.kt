/*
 *
 *  Created by paulz on 2023/6/7 上午7:20
 *  Last modified 2023/6/7 上午7:20
 */

package com.example.taipeitravel.uiAttractions.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.taipeitravel.R
import com.example.taipeitravel.databinding.ItemAttractionBinding
import com.example.taipeitravel.model.Attraction.Attraction


class AttractionsAdapter(val attractionsAdapterInterface: AttractionsAdapterInterface) :
    ListAdapter<Attraction, ViewHolder>(DiffCallBack()) {
    //add onClick interface to this adapter
    interface AttractionsAdapterInterface {
        fun attractionSelected(attraction: Attraction)
    }

    class DiffCallBack : DiffUtil.ItemCallback<Attraction>() {

        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.introduction == newItem.introduction
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return AttractionViewHolder(ItemAttractionBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as AttractionViewHolder).also {
            Log.d("TAG", "onBindViewHolder: ${getItem(position).name}")
            it.binding.tvTitle.text = getItem(position).name
            it.binding.tvContent.text = getItem(position).introduction
            if (getItem(position).images.isNotEmpty())
                it.binding.img.load(getItem(position).images[0].src) {
                    placeholder(R.drawable.img_empty_img)
                    crossfade(true)
                }
            else
                it.binding.img.load(R.drawable.img_empty_img)
            it.binding.layout.setOnClickListener {
                attractionsAdapterInterface.attractionSelected(attraction = getItem(position))
            }
        }
    }

    class AttractionViewHolder(val binding: ItemAttractionBinding) : ViewHolder(binding.root)
}

