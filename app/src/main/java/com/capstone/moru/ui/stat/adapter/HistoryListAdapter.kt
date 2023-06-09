package com.capstone.moru.ui.stat.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.ItemStatisticHsitoryBinding
import com.capstone.moru.ui.detail.user_routine.DetailUserBookRoutineActivity
import com.capstone.moru.ui.detail.user_routine.DetailUserExerciseRoutineActivity
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import com.capstone.moru.utils.ScheduleDiffUtilCallback

class HistoryListAdapter(private val listSchedule: List<ScheduleListItem?>?) :
    ListAdapter<ScheduleListItem, HistoryListAdapter.ViewHolder>(ScheduleDiffUtilCallback()) {

    class ViewHolder(val binding: ItemStatisticHsitoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun getItemCount(): Int {
        return listSchedule?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticHsitoryBinding.inflate(
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
            binding.tvShceduleDate.text = schedule.date
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(schedule)
            val intentToScheduleDetail = if (schedule?.refId!! > 100) {
                Intent(holder.itemView.context, DetailUserBookRoutineActivity::class.java)
            } else {
                Intent(holder.itemView.context, DetailUserExerciseRoutineActivity::class.java)
            }

            intentToScheduleDetail.putExtra(ScheduleListAdapter.KEY_ID_SCHEDULE, schedule?.id)
            holder.itemView.context.startActivity(intentToScheduleDetail)
        }

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(routineBooks: com.capstone.moru.data.api.response.ScheduleListItem?)
    }
}