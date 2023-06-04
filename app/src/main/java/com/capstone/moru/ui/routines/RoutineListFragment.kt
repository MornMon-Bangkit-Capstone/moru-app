package com.capstone.moru.ui.routines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.moru.databinding.FragmentRoutineListBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.RoutineListAdapter
import com.capstone.moru.utils.LoadingStateListAdapter


class RoutineListFragment : Fragment() {
    private var _binding: FragmentRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }
    private lateinit var routineLisAdapter: RoutineListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())

        setRoutinesData()
    }

    private fun setRoutinesData() {
        routineViewModel.getAllExercise()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvRoutine.layoutManager = layoutManager

        routineLisAdapter = RoutineListAdapter()
        routineLisAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    layoutManager.scrollToPosition(0)
                }
            }
        })

        routineViewModel.userExerciseRoutines.observe(viewLifecycleOwner) {
            routineLisAdapter.submitData(lifecycle, it)
        }

//        binding.rvRoutine.adapter = routineLisAdapter.withLoadStateFooter(
//            footer = LoadingStateListAdapter { routineLisAdapter.retry() }
//        )

        binding.rvRoutine.adapter = routineLisAdapter
    }

    companion object {
        const val POSITION = "section_number"
    }
}