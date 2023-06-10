package com.capstone.moru.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.R
import com.capstone.moru.databinding.FragmentRoutinesBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.RoutinesSectionAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RoutinesFragment : Fragment() {
    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickRoutineSectionsAdapter = RoutinesSectionAdapter(this)
        binding.viewPager.adapter = pickRoutineSectionsAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.read,
            R.string.exercise,
        )

    }
}