package com.capstone.moru.ui.schedule

import android.os.Bundle
import android.util.Log
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
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val scheduleViewModel: ScheduleViewModel by viewModels { factory }

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var tokenUser: String? = null
    private var emptyListRoutine: List<ScheduleListItem?>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("SCHEDULE", selectedDate.toString())
        factory = ViewModelFactory.getInstance(requireContext())

        binding.rvSchedule.isNestedScrollingEnabled = false

        binding.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                selectedDate = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)
                scheduleViewModel.getCurrentSchedule(tokenUser!!, selectedDate!!)
            }
        )

        scheduleViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            tokenUser = token
            scheduleViewModel.getCurrentSchedule(token, selectedDate!!)
            Log.e("Token", token.toString())
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root

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
            initRecyclerView(emptyListRoutine)
        } else {
            binding.btnRetry.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}