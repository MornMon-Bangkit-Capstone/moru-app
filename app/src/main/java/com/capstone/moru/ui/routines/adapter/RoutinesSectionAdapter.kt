package com.capstone.moru.ui.routines.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineListFragment
import com.capstone.moru.ui.routines.BooksRoutineListFragment
import com.capstone.moru.ui.routines.ExerciseRoutineListFragment
import com.capstone.moru.ui.routines.RoutineListFragment

class RoutinesSectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
//        fragment.arguments = Bundle().apply {
//            putInt(RoutineListFragment.POSITION, position + 1)
//        }
        when(position){
            0 -> fragment = BooksRoutineListFragment()
            1 -> fragment = ExerciseRoutineListFragment()
        }

        return fragment as Fragment
    }
}