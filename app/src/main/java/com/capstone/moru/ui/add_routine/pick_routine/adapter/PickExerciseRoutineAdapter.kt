package com.capstone.moru.ui.add_routine.pick_routine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ExerciseListItem
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.utils.ExerciseRoutineDiffUtil
import com.capstone.moru.utils.PickExerciseRoutineDataClass

class PickExerciseRoutineAdapter(
    private val listExerciseRoutine: List<ExerciseListItem?>?,
    private val recyclerView: RecyclerView,
) : ListAdapter<ExerciseListItem, PickExerciseRoutineAdapter.ViewHolder>(ExerciseRoutineDiffUtil()) {

    class ViewHolder(val binding: ItemRoutinePickBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var selectedItemPosition: Int = -1

    override fun getItemCount(): Int {
        return listExerciseRoutine?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRoutinePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseRoutine = listExerciseRoutine?.get(position)
        val pickedRoutine = listExerciseRoutine?.map {
            PickExerciseRoutineDataClass(it)
        }

        val placeholder =
            holder.itemView.context.resources.getDrawable(R.drawable.placeholder_exercise)
        holder.apply {
            Glide.with(holder.itemView.context).load(exerciseRoutine?.visual)
                .into(holder.binding.imageView).onLoadFailed(placeholder)
            binding.customCategory.text = exerciseRoutine?.category
            binding.textView.text = exerciseRoutine?.sports
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(exerciseRoutine)
            updateItemSelected(holder, position, pickedRoutine)
        }

        holder.binding.radioButton.setOnClickListener {
            onItemClickCallback.onItemClicked(exerciseRoutine)
            updateItemSelected(holder, position, pickedRoutine)
        }
    }

    private fun updateItemSelected(
        holder: ViewHolder,
        position: Int,
        pickedRoutine: List<PickExerciseRoutineDataClass>?
    ) {
        if (selectedItemPosition != position && selectedItemPosition != -1) {
            val prevHolder = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition)
            prevHolder.let {
                val prevRadioButton = it?.itemView?.findViewById<CheckBox>(R.id.radioButton)
                prevRadioButton?.isChecked = false
                notifyItemChanged(selectedItemPosition)
            }
            holder.binding.radioButton.isChecked =
                pickedRoutine?.get(selectedItemPosition)?.isChecked!!
        }

        selectedItemPosition = position
        pickedRoutine?.get(position)?.isChecked = !pickedRoutine?.get(position)?.isChecked!!
        holder.binding.radioButton.isChecked = pickedRoutine[position]!!.isChecked
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(item: ExerciseListItem?)
    }
}