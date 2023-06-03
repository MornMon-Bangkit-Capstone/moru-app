package com.capstone.moru.ui.routines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.data.db.model.RoutineModel
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.utils.DiffUtilCallback
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class RoutineListAdapter : PagingDataAdapter<RoutineModel, RoutineListAdapter.ViewHolder>(DiffUtilCallback()) {

   class ViewHolder(val binding: ItemRoutinePickBinding): RecyclerView.ViewHolder(binding.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = ItemRoutinePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val routine = getItem(position)

      holder.apply {
         Glide.with(holder.itemView.context).load(routine?.imgUrl).into(holder.binding.imageView)
         binding.textView.text = routine?.title
         binding.customCategory.text = routine?.type
      }
   }
}