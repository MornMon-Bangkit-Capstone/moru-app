package com.capstone.moru.ui.add_routine.pick_routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ExerciseListItem
import com.capstone.moru.databinding.FragmentPickExerciseRoutineBinding
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickExerciseRoutineAdapter
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.RoutineViewModel


class PickExerciseRoutineFragment : Fragment() {
    private var _binding: FragmentPickExerciseRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        routineViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            routineViewModel.getAllExerciseRoutine(token)
        }

        routineViewModel.exerciseRoutine.observe(viewLifecycleOwner) { routines ->
            initRecyclerView(routines)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickExerciseRoutineBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun initRecyclerView(routines: List<ExerciseListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val recyclerView = binding.rvRoutine
        val adapter = PickExerciseRoutineAdapter(routines, recyclerView)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object : PickExerciseRoutineAdapter.OnItemClickCallback {
            override fun onItemClicked(item: ExerciseListItem?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}