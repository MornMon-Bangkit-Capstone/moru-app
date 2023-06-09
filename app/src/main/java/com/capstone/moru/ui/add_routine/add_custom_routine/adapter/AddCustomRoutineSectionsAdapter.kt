package com.capstone.moru.ui.add_routine.add_custom_routine.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.moru.ui.add_routine.add_custom_routine.AddReadFragment

class AddCustomRoutineSectionsAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       var fragment: Fragment? = null
        when(position){
            0 -> fragment = AddReadFragment()
            1 -> fragment = AddReadFragment()
        }

        return fragment as Fragment
    }
}