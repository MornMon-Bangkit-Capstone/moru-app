package com.capstone.moru.ui.routines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ExerciseListItem
import com.capstone.moru.databinding.FragmentExerciseRoutineListBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.ExerciseRoutineListAdapter

class ExerciseRoutineListFragment : Fragment() {
    private var _binding: FragmentExerciseRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseRoutineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())

        routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            routineViewModel.getAllExerciseRoutine(token)
        }

        routineViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        routineViewModel.exerciseRoutine.observe(viewLifecycleOwner) { routine ->
            Log.e("TEST", routine.toString())
            initRecyclerView(routine)
        }

    }

    private fun initRecyclerView(routine: List<ExerciseListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val adapter = ExerciseRoutineListAdapter(routine)
        binding.rvRoutine.adapter = adapter

        adapter.setOnItemClickCallback(object : ExerciseRoutineListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineExercise: ExerciseListItem?) {
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}