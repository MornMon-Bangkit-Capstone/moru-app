package com.capstone.moru.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.FragmentScheduleBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import java.time.LocalDate

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val scheduleViewModel: ScheduleViewModel by viewModels { factory }
    private var selectedDate: String? = LocalDate.now().toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        binding.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                selectedDate = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)
                displayToast(selectedDate!!)
            }
        )

        scheduleViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            scheduleViewModel.getCurrentSchedule(token, selectedDate!!)
        }

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        scheduleViewModel.schedule.observe(viewLifecycleOwner) { schedule ->
            initRecyclerView(schedule)
        }

        scheduleViewModel.error.observe(viewLifecycleOwner) {
            retry(it)
        }

    }

    private fun initRecyclerView(schedule: List<ScheduleListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.layoutManager = layoutManager

        val recyclerView = binding.rvSchedule
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
            binding.btnRetry.apply {
                visibility = View.VISIBLE
                isEnabled = true

                setOnClickListener {
                    scheduleViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                        scheduleViewModel.getCurrentSchedule(token, selectedDate!!)
                    }
                    visibility = View.GONE
                    isEnabled = false
                }
            }
        } else {
            binding.btnRetry.apply {
                visibility = View.GONE
                isEnabled = false
            }
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root

    }

}