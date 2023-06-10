package com.capstone.moru.ui.routines.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.moru.ui.routines.BooksRoutineListFragment
import com.capstone.moru.ui.routines.ExerciseRoutineListFragment

class RoutinesSectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = BooksRoutineListFragment()
            1 -> fragment = ExerciseRoutineListFragment()
        }

        return fragment as Fragment
    }
}