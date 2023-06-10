package com.capstone.moru.ui.routines.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ExerciseListItem
import com.capstone.moru.databinding.ItemRoutinesBinding
import com.capstone.moru.ui.detail.exercise_routine.DetailExerciseActivity
import com.capstone.moru.utils.ExerciseRoutineDiffUtil

class ExerciseRoutineListAdapter(private val listExerciseRoutine: List<com.capstone.moru.data.api.response.ExerciseListItem?>?) :
    ListAdapter<com.capstone.moru.data.api.response.ExerciseListItem, ExerciseRoutineListAdapter.ViewHolder>(ExerciseRoutineDiffUtil()) {

    class ViewHolder(val binding: ItemRoutinesBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRoutinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routineExercise = listExerciseRoutine?.get(position)
        val placeholder = holder.itemView.context.resources.getDrawable(R.drawable.placeholder_book)

        holder.apply {
            Glide.with(holder.itemView.context).load(routineExercise?.visual)
                .into(holder.binding.ivCardRoutine).onLoadFailed(placeholder)
            binding.customCategory.text = routineExercise?.category
            binding.tvCardRoutineName.text = routineExercise?.sports
            binding.tvRoutineDesc.text = routineExercise?.description
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(routineExercise)
            val intentToExerciseDetail = Intent(holder.itemView.context, DetailExerciseActivity::class.java)
            intentToExerciseDetail.putExtra(KEY_EXERCISE_ROUTINE, routineExercise?.id)
            intentToExerciseDetail.putExtra(KEY_ID_EXERCISE, routineExercise?.isPublic)
            holder.itemView.context.startActivity(intentToExerciseDetail)
        }
    }

    override fun getItemCount(): Int {
        return listExerciseRoutine!!.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(routineExercise: com.capstone.moru.data.api.response.ExerciseListItem?)
    }

    companion object {
        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
        const val KEY_ID_EXERCISE = "key_id_exercise"
    }
}