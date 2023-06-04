package com.capstone.moru.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.moru.databinding.FragmentRoutineListBinding
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineListFragment.Companion.POSITION
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.BooksRoutineListAdapter
import com.capstone.moru.ui.routines.adapter.ExerciseRoutineListAdapter


class RoutineListFragment : Fragment() {
    private var _binding: FragmentRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }
    private lateinit var exerciseRoutineListAdapter: ExerciseRoutineListAdapter
    private lateinit var bookRoutineListAdapter: BooksRoutineListAdapter

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
//        val position = arguments?.getInt(POSITION)

//        if (position == 2) {
//            routineViewModel.getAllExercise()
//        }else{
//            routineViewModel.getAllBooks()
//        }

        routineViewModel.getAllExercise()
        routineViewModel.getAllBooks()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvRoutine.layoutManager = layoutManager

        exerciseRoutineListAdapter = ExerciseRoutineListAdapter()
        bookRoutineListAdapter = BooksRoutineListAdapter()

        exerciseRoutineListAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    layoutManager.scrollToPosition(0)
                }
            }
        })
        bookRoutineListAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    layoutManager.scrollToPosition(0)
                }
            }
        })

        routineViewModel.userExerciseRoutines.observe(viewLifecycleOwner) {
            exerciseRoutineListAdapter.submitData(lifecycle, it)
        }

        routineViewModel.userBooksRoutines.observe(viewLifecycleOwner) {
            bookRoutineListAdapter.submitData(lifecycle, it)
        }

//        binding.rvRoutine.adapter = routineLisAdapter.withLoadStateFooter(
//            footer = LoadingStateListAdapter { routineLisAdapter.retry() }
//        )

        val position = arguments?.getInt(POSITION)

        if (position == 1){
            binding.rvRoutine.adapter = bookRoutineListAdapter
        }else{
            binding.rvRoutine.adapter = exerciseRoutineListAdapter
        }
//        binding.rvRoutine.adapter = exerciseRoutineListAdapter
//        binding.rvRoutine.adapter = bookRoutineListAdapter

    }

    companion object {
        const val POSITION = "section_number"
    }
}