package com.capstone.moru.ui.add_routine.pick_routine.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.moru.R
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.databinding.ItemRoutinePickBinding
import com.capstone.moru.ui.add_routine.pick_schedule.PickScheduleActivity
import com.capstone.moru.utils.BookRoutineDiffUtil
import com.capstone.moru.utils.PickBookRoutineDataClass

class PickBookRoutineAdapter(
    private val listBookRoutine: List<BookListItem?>?,

    ) : ListAdapter<BookListItem, PickBookRoutineAdapter.ViewHolder>(BookRoutineDiffUtil()) {

    class ViewHolder(val binding: ItemRoutinePickBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val pickedRoutine = listBookRoutine?.map {
        PickBookRoutineDataClass(it)
    }

    override fun getItemCount(): Int {
        return listBookRoutine?.size!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemRoutinePickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookRoutine = listBookRoutine?.get(position)
        val formattedRoutine = bookRoutine?.genres?.substring(
            bookRoutine.genres.indexOf("[") + 1,
            bookRoutine.genres.indexOf("]")
        )?.split(",")?.map {
            it.trim().replace("^['\"]|['\"]$".toRegex(), "")
        }

        Log.e("TEST", pickedRoutine?.size.toString())

        val placeholder = holder.itemView.context.resources.getDrawable(R.drawable.placeholder_book)
        holder.apply {
            binding.customCategory.text = formattedRoutine?.firstOrNull()
            binding.textView.text = bookRoutine?.bookTitle
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(bookRoutine)

            val book = "Book"
            val intentToPickSchedule =
                Intent(holder.itemView.context, PickScheduleActivity::class.java)
            intentToPickSchedule.putExtra(KEY_BOOK_ROUTINE, bookRoutine?.bookTitle)
            intentToPickSchedule.putExtra(KEY_ID_BOOK, book)

            intentToPickSchedule.putExtra(KEY_IS_PUBLIC, bookRoutine?.isPublic)
            intentToPickSchedule.putExtra(KEY_REF_ID, bookRoutine?.iSBN)

            holder.itemView.context.startActivity(intentToPickSchedule)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(item: com.capstone.moru.data.api.response.BookListItem?)
    }

    companion object {
        const val KEY_BOOK_ROUTINE = "key_book_routine"
        const val KEY_ID_BOOK = "key_id_book"

        const val KEY_REF_ID = "key_ref_id"
        const val KEY_IS_PUBLIC = "key_is_public"
    }
}