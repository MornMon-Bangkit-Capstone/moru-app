package com.capstone.moru.ui.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ItemRoutinesBinding
import com.capstone.moru.utils.DiffUtilCallback

class RoutineListAdapter(private val listRoutine: List<ListItem?>?) :
    ListAdapter<ListItem, RoutineListAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(val binding: ItemRoutinesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return listRoutine!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRoutinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = listRoutine?.get(position)

        holder.apply {
            Glide.with(holder.itemView.context).load(routine?.imgUrl)
                .into(holder.binding.ivCardRoutine)
            binding.customCategory.text = routine?.type
            binding.tvCardRoutineName.text = routine?.title
            binding.tvRoutineDesc.text = routine?.description
        }
    }


}