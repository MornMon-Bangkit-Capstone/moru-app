package com.capstone.moru.ui.add_routine.pick_routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        routineViewModel.error.observe(viewLifecycleOwner){
            retry(it)
        }

        routineViewModel.message.observe(viewLifecycleOwner){
            displayToast(it)
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun retry(it: Boolean?) {
        if (it!!){
            binding.progressBar.visibility = View.GONE
            binding.btnRetry.apply {
                visibility = View.VISIBLE
                isEnabled = true
                setOnClickListener {
                    routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                        routineViewModel.getAllBooksRoutine(token)
                    }
                    visibility = View.GONE
                    isEnabled = false
                }
            }
        }else{
            binding.btnRetry.apply {
                visibility = View.GONE
                isEnabled = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickExerciseRoutineBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun initRecyclerView(routines: List<com.capstone.moru.data.api.response.ExerciseListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val recyclerView = binding.rvRoutine
        val adapter = PickExerciseRoutineAdapter(routines, recyclerView)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object : PickExerciseRoutineAdapter.OnItemClickCallback {
            override fun onItemClicked(item: com.capstone.moru.data.api.response.ExerciseListItem?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}