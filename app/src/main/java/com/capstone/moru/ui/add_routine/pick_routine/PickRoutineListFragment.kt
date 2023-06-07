package com.capstone.moru.ui.add_routine.pick_routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.FragmentPickRoutineListBinding
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickRoutineListAdapter
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.RoutineListFragment
import com.capstone.moru.ui.routines.RoutineViewModel

class PickRoutineListFragment : Fragment() {
    private var _binding: FragmentPickRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }
    private var itemClickListener: OnItemClickListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickRoutineListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())

        setRoutinesData()

        routineViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        routineViewModel.bookRoutine.observe(viewLifecycleOwner) { routines ->
            initRecyclerView(routines)
        }

        routineViewModel.exerciseRoutine.observe(viewLifecycleOwner) { routines ->
            initRecyclerView(routines)
        }
    }

    private fun setRoutinesData() {
        val position = arguments?.getInt(RoutineListFragment.POSITION)
        if (position == 1) {
            routineViewModel.getAllBooksRoutine()
        } else {
            routineViewModel.getAllExerciseRoutine()
        }
    }

    private fun initRecyclerView(routines: List<ListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val recyclerView = binding.rvRoutine
        val adapter = PickRoutineListAdapter(routines, recyclerView)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object: PickRoutineListAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ListItem?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    interface OnItemClickListener {
        fun onItemClicked(item: ListItem)
    }

    companion object {
        const val POSITION = "section_number"
    }
}