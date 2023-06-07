package com.capstone.moru.ui.routines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.FragmentRoutineListBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.RoutineListAdapter


class RoutineListFragment : Fragment() {
    private var _binding: FragmentRoutineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    //    private lateinit var exerciseRoutineListAdapter: ExerciseRoutineListAdapter
//    private lateinit var bookRoutineListAdapter: BooksRoutineListAdapter
//    private lateinit var routineListAdapter: RoutineListAdapter

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

//        if (position == 2) {
//            routineViewModel.getAllExercise()
//        }else{
//            routineViewModel.getAllBooks()
//        }

//        routineViewModel.getAllExercise()
//        routineViewModel.getAllBooks()


        val position = arguments?.getInt(POSITION)
        if (position == 1) {
            routineViewModel.getAllBooksRoutine()
        } else {
            routineViewModel.getAllExerciseRoutine()
        }
    }

    private fun initRecyclerView(routines: List<ListItem?>?) {
//        val layoutManager = LinearLayoutManager(activity)
//        binding.rvRoutine.layoutManager = layoutManager
//
//        exerciseRoutineListAdapter = ExerciseRoutineListAdapter()
//        bookRoutineListAdapter = BooksRoutineListAdapter()
//
//        exerciseRoutineListAdapter.registerAdapterDataObserver(object :
//            RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                if (positionStart == 0) {
//                    layoutManager.scrollToPosition(0)
//                }
//            }
//        })
//        bookRoutineListAdapter.registerAdapterDataObserver(object :
//            RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                if (positionStart == 0) {
//                    layoutManager.scrollToPosition(0)
//                }
//            }
//        })
//
//        routineViewModel.userExerciseRoutines.observe(viewLifecycleOwner) {
//            exerciseRoutineListAdapter.submitData(lifecycle, it)
//        }
//
//        routineViewModel.userBooksRoutines.observe(viewLifecycleOwner) {
//            bookRoutineListAdapter.submitData(lifecycle, it)
//        }
//
////        binding.rvRoutine.adapter = routineLisAdapter.withLoadStateFooter(
////            footer = LoadingStateListAdapter { routineLisAdapter.retry() }
////        )
//
//        val position = arguments?.getInt(POSITION)
//
//        if (position == 1){
//            binding.rvRoutine.adapter = bookRoutineListAdapter
//        }else{
//            binding.rvRoutine.adapter = exerciseRoutineListAdapter
//        }
////        binding.rvRoutine.adapter = exerciseRoutineListAdapter
////        binding.rvRoutine.adapter = bookRoutineListAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager
        val adapter = RoutineListAdapter(routines)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object : RoutineListAdapter.OnItemClickCallback {
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