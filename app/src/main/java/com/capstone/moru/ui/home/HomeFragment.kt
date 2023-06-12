package com.capstone.moru.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.FragmentHomeBinding
import com.capstone.moru.databinding.FragmentScheduleBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.schedule.ScheduleViewModel
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val scheduleViewModel: ScheduleViewModel by viewModels { factory }

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        binding.rvSchedule.isNestedScrollingEnabled = false

        scheduleViewModel.getUserToken().observe(viewLifecycleOwner){
            token -> scheduleViewModel.getCurrentSchedule(token, selectedDate!!)
        }

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        scheduleViewModel.schedule.observe(viewLifecycleOwner) { schedule ->
            Log.e("LIST SCHEDULE", schedule?.size.toString())
            initRecyclerView(schedule)
        }

        scheduleViewModel.error.observe(viewLifecycleOwner) {
            retry(it)
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
//            binding.btnRetry.apply {
//                visibility = View.VISIBLE
//                isEnabled = true
//
//                setOnClickListener {
//                    scheduleViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
//                        scheduleViewModel.getCurrentSchedule(token, selectedDate!!)
//                    }
//                    visibility = View.GONE
//                    isEnabled = false
//                }
//            }
            binding.btnRetry.visibility = View.VISIBLE
        } else {
            binding.btnRetry.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isLoading) View.GONE else View.VISIBLE
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}