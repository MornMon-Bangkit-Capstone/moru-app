package com.capstone.moru.ui.schedule.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.ItemRoutineUserBinding
import com.capstone.moru.ui.detail.user_routine.DetailUserBookRoutineActivity
import com.capstone.moru.ui.detail.user_routine.DetailUserExerciseRoutineActivity
import com.capstone.moru.utils.ScheduleDiffUtilCallback

class ScheduleListAdapter(private val listSchedule: List<ScheduleListItem?>?) :
    ListAdapter<ScheduleListItem, ScheduleListAdapter.ViewHolder>(ScheduleDiffUtilCallback()) {

    class ViewHolder(val binding: ItemRoutineUserBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRoutineUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = listSchedule?.get(position)
        val formatDetailSchedule = "${schedule?.startTime} - ${schedule?.endTime}"
        val formatStatus = when (schedule?.status) {
            "NOT_STARTED" -> {
                "Not started"
            }
            "COMPLETED" -> {
                "Completed"
            }
            else -> {
                "Skipped"
            }
        }

        holder.apply {
            if (schedule?.type != "BOOK") {
                binding.ivRoutine.setImageResource(R.drawable.placeholder_exercise)
            }

            binding.tvRoutineName.text = schedule?.name
            binding.tvRoutineDetail.text = formatDetailSchedule
            holder.binding.scheduleStatus.setStatus(schedule?.status!!)
            binding.scheduleStatus.text = formatStatus
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(schedule)
            val intentToScheduleDetail = if (schedule?.refId!! > 100) {
                Intent(holder.itemView.context, DetailUserBookRoutineActivity::class.java)
            } else {
                Intent(holder.itemView.context, DetailUserExerciseRoutineActivity::class.java)
            }

            intentToScheduleDetail.putExtra(KEY_ID_SCHEDULE, schedule?.id)
            holder.itemView.context.startActivity(intentToScheduleDetail)
        }
    }

    override fun getItemCount(): Int {
        return listSchedule?.size ?: 0
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(routineBooks: com.capstone.moru.data.api.response.ScheduleListItem?)
    }

    companion object {
        const val KEY_ID_SCHEDULE = "key_id_schedule"
    }
}