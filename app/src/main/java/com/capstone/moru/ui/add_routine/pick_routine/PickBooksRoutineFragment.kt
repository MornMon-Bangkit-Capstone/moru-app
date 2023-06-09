package com.capstone.moru.ui.add_routine.pick_routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.databinding.FragmentPickBooksRoutineBinding
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickBookRoutineAdapter
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.RoutineViewModel


class PickBooksRoutineFragment : Fragment() {
    private var _binding: FragmentPickBooksRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val routineViewModel: RoutineViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickBooksRoutineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        routineViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        routineViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            routineViewModel.getAllBooksRoutine(token)
        }

        routineViewModel.bookRoutine.observe(viewLifecycleOwner) { routines ->
            initRecyclerView(routines)
        }
    }

    private fun initRecyclerView(routines: List<BookListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoutine.layoutManager = layoutManager

        val recyclerView = binding.rvRoutine
        val adapter = PickBookRoutineAdapter(routines, recyclerView)

        binding.rvRoutine.adapter = adapter
        adapter.setOnItemClickCallback(object : PickBookRoutineAdapter.OnItemClickCallback {
            override fun onItemClicked(item: BookListItem?) {
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}