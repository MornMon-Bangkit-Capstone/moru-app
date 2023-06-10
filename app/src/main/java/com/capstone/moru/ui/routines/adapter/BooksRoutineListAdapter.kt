package com.capstone.moru.ui.routines.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.databinding.ItemRoutinesBinding
import com.capstone.moru.ui.detail.book_routine.DetailBookActivity
import com.capstone.moru.ui.detail.exercise_routine.DetailExerciseActivity
import com.capstone.moru.utils.BookRoutineDiffUtil

class BooksRoutineListAdapter(private val listBookRoutine: List<com.capstone.moru.data.api.response.BookListItem?>?) :
    ListAdapter<com.capstone.moru.data.api.response.BookListItem, BooksRoutineListAdapter.ViewHolder>(
        BookRoutineDiffUtil()
    ) {

    class ViewHolder(val binding: ItemRoutinesBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRoutinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routineBooks = listBookRoutine?.get(position)
        val formattedRoutine = routineBooks?.genres?.substring(
            routineBooks.genres.indexOf("[") + 1,
            routineBooks.genres.indexOf("]")
        )?.split(",")?.map {
            it.trim()
        }
        val placeholder = holder.itemView.context.resources.getDrawable(R.drawable.placeholder_book)

        holder.apply {
            Glide.with(holder.itemView.context).load(routineBooks?.imageURLL)
                .into(holder.binding.ivCardRoutine).onLoadFailed(placeholder)
            binding.customCategory.text = formattedRoutine?.firstOrNull()
            binding.tvCardRoutineName.text = routineBooks?.bookTitle
            binding.tvRoutineDesc.text = routineBooks?.summary
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(routineBooks)
            val intentToBookDetail =
                Intent(holder.itemView.context, DetailBookActivity::class.java)
            intentToBookDetail.putExtra(KEY_BOOK_ROUTINE, routineBooks?.iSBN)
            intentToBookDetail.putExtra(KEY_ID_BOOK, routineBooks?.isPublic)
            holder.itemView.context.startActivity(intentToBookDetail)
        }
    }

    override fun getItemCount(): Int {
        return listBookRoutine!!.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(routineBooks: com.capstone.moru.data.api.response.BookListItem?)
    }

    companion object {
        const val KEY_BOOK_ROUTINE = "key_book_routine"
        const val KEY_ID_BOOK = "key_id_book"
    }
}