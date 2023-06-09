package com.capstone.moru.ui.routines.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ItemRoutinesBinding
import com.capstone.moru.ui.detail.book_routine.DetailBookActivity
import com.capstone.moru.ui.detail.exercise_routine.DetailExerciseActivity
import com.capstone.moru.utils.DiffUtilCallback

class RoutineListAdapter(private val listRoutine: List<ListItem?>?) :
    ListAdapter<ListItem, RoutineListAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(val binding: ItemRoutinesBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(routine)

            val routineId = routine?.id?.subSequence(0, 8)

            if (routineId == "exercise") {
                val intentToExerciseDetail =
                    Intent(holder.itemView.context, DetailExerciseActivity::class.java)
                intentToExerciseDetail.putExtra(KEY_EXERCISE_ROUTINE, routine.id)
                holder.itemView.context.startActivity(intentToExerciseDetail)
            } else {
                val intentToBookDetail =
                    Intent(holder.itemView.context, DetailBookActivity::class.java)
                intentToBookDetail.putExtra(KEY_BOOK_ROUTINE, routine?.id)
                holder.itemView.context.startActivity(intentToBookDetail)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ListItem?)
    }

    companion object {
        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
        const val KEY_BOOK_ROUTINE = "key_book_routine"
    }
}