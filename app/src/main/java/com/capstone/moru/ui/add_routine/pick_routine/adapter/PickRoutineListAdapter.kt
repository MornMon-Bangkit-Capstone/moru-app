package com.capstone.moru.ui.add_routine.pick_routine.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.utils.DiffUtilCallback
import com.capstone.moru.utils.PickRoutineDataClass

class PickRoutineListAdapter(
    private val listRoutine: List<ListItem?>?,
    private val recyclerView: RecyclerView,
    private val sectionPosition: Int?,
) :
    ListAdapter<ListItem, PickRoutineListAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(val binding: ItemRoutinePickBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var selectedItemPosition: Int = -1
    private var resetRadioButtons: Boolean = false

    var userPickedRoutine: ListItem? = null
    var tempBooksRoutine: List<PickRoutineDataClass>? = null
    var tempExerciseRoutine: List<PickRoutineDataClass>? = null


    private fun populateData(position: Int) {
        if (position == 1) {
            tempBooksRoutine = listRoutine?.map {
                PickRoutineDataClass(it)
            }
        } else {
            tempExerciseRoutine = listRoutine?.map {
                PickRoutineDataClass(it)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemRoutinePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (sectionPosition != null) {
            populateData(sectionPosition)
        }

        val routine = listRoutine?.get(position)
        val pickRoutine = if (sectionPosition == 1) {
            tempBooksRoutine
        } else {
            tempExerciseRoutine
        }

        holder.apply {
            Glide.with(holder.itemView.context).load(routine?.imgUrl).into(holder.binding.imageView)
            binding.customCategory.text = routine?.type
            binding.textView.text = routine?.title
        }

        holder.binding.radioButton.isChecked = selectedItemPosition == position

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(routine)
            updateItemSelected(holder, position, pickRoutine)
        }

        holder.binding.radioButton.setOnClickListener {
            onItemClickCallback.onItemClicked(routine)
            updateItemSelected(holder, position, pickRoutine)
        }

        if (resetRadioButtons) {
            pickRoutine?.get(selectedItemPosition)?.isChecked = false
            holder.binding.radioButton.isChecked = false
        }

    }

    override fun getItemCount(): Int {
        return listRoutine!!.size
    }

    private fun updateItemSelected(
        holder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int,
        pickedRoutine: List<PickRoutineDataClass>?,
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
        holder.binding.radioButton.isChecked = pickedRoutine[position].isChecked
        userPickedRoutine = pickedRoutine[position].routine

        Log.e("RESET", resetRadioButtons.toString())
    }

    fun resetRadioButtons(reset: Boolean) {
        resetRadioButtons = reset
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(item: ListItem?)
    }
}