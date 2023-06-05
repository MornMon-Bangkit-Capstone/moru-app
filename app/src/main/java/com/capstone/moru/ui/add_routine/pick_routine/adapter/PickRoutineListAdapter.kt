package com.capstone.moru.ui.add_routine.pick_routine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.databinding.ItemRoutinesBinding
import com.capstone.moru.ui.routines.adapter.RoutineListAdapter
import com.capstone.moru.utils.DiffUtilCallback

class PickRoutineListAdapter(private val listRoutine: List<ListItem?>?) :
    ListAdapter<ListItem, PickRoutineListAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(val binding: ItemRoutinePickBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRoutinePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = listRoutine?.get(position)

        holder.apply {
            Glide.with(holder.itemView.context).load(routine?.imgUrl).into(holder.binding.imageView)
            binding.customCategory.text = routine?.type
            binding.textView.text = routine?.title
        }
    }

    override fun getItemCount(): Int {
        return listRoutine!!.size
    }

}