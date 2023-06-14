package com.capstone.moru.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.FragmentHomeBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.schedule.ScheduleViewModel
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var emptyListRoutine: List<ScheduleListItem?>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        binding.rvSchedule.isNestedScrollingEnabled = false

        homeViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            homeViewModel.getCurrentSchedule(token, selectedDate!!)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.schedule.observe(viewLifecycleOwner) { schedule ->
            initRecyclerView(schedule)
        }

        homeViewModel.error.observe(viewLifecycleOwner) {
            retry(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        homeViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            homeViewModel.getCurrentSchedule(token, selectedDate!!)
        }
    }

    private fun initRecyclerView(schedule: List<ScheduleListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.layoutManager = layoutManager

        val adapter = ScheduleListAdapter(schedule)

        binding.rvSchedule.adapter = adapter
        adapter.setOnItemClickCallback(object : ScheduleListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineBooks: ScheduleListItem?) {
            }
        })
    }

    private fun retry(it: Boolean?) {
        if (it!!) {
            binding.progressBar.visibility = View.GONE
            binding.btnRetry.visibility = View.VISIBLE
            initRecyclerView(emptyListRoutine)
        } else {
            binding.btnRetry.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}