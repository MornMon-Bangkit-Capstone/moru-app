package com.capstone.moru.ui.add_routine.pick_routine.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.moru.ui.add_routine.pick_routine.PickBooksRoutineFragment
import com.capstone.moru.ui.add_routine.pick_routine.PickExerciseRoutineFragment

class PickRoutineSectionsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PickBooksRoutineFragment()
            1 -> fragment = PickExerciseRoutineFragment()
        }

        return fragment as Fragment
    }
}