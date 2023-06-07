package com.capstone.moru.ui.add_routine.pick_routine.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.utils.DiffUtilCallback

class PickRoutineListAdapter(
    private val listRoutine: List<ListItem?>?,
    private val recyclerView: RecyclerView
) :
    ListAdapter<ListItem, PickRoutineListAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(val binding: ItemRoutinePickBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var selectedItemPosition: Int = -1
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
        val routine = listRoutine?.get(position)

        holder.apply {
            Glide.with(holder.itemView.context).load(routine?.imgUrl).into(holder.binding.imageView)
            binding.customCategory.text = routine?.type
            binding.textView.text = routine?.title
        }

        holder.binding.radioButton.isChecked = selectedItemPosition == position
        holder.binding.radioButton.apply {
            holder.binding.radioButton.isChecked = selectedItemPosition == position
        }


        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(routine)
            updateItemSelected(holder, position)
        }

        holder.binding.radioButton.setOnClickListener {
            onItemClickCallback.onItemClicked(routine)
            updateItemSelected(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return listRoutine!!.size
    }

    private fun updateItemSelected(
        holder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (selectedItemPosition != position) {
            val prevHolder = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition)
            prevHolder.let {
                val prevRadioButton = it?.itemView?.findViewById<RadioButton>(R.id.radioButton)
                prevRadioButton?.isChecked = false

                val prevCard = it?.itemView?.findViewById<ConstraintLayout>(R.id.card_pick_routine)
                prevCard?.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.custom_pick_routince_card
                )
            }
        } else {
            holder.binding.radioButton.isChecked = false
            holder.binding.cardPickRoutine.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.custom_pick_routince_card
            )
        }

        selectedItemPosition = position
        holder.binding.radioButton.isChecked = true
        holder.binding.cardPickRoutine.background = ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.custom_pick_routine_card_border
        )
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ListItem?)
    }
}