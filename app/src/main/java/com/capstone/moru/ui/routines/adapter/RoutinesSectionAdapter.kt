package com.capstone.moru.ui.routines.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineListFragment

class RoutinesSectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = PickRoutineListFragment()
        fragment.arguments = Bundle().apply {
            putInt(PickRoutineListFragment.POSITION, position + 1)
        }

        return fragment
    }
}