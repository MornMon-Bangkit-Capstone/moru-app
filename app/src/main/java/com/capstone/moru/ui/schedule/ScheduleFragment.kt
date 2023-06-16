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
import com.capstone.moru.ui.alarm.receiver.AlarmReceiver
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val scheduleViewModel: ScheduleViewModel by viewModels { factory }

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var tokenUser: String? = null
    private var emptyListRoutine: List<ScheduleListItem?>? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext())

        binding.rvSchedule.isNestedScrollingEnabled = false
        alarmReceiver = AlarmReceiver()

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
        }

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        scheduleViewModel.schedule.observe(viewLifecycleOwner) { schedule ->
            refreshAlarm(schedule)
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

    private fun refreshAlarm(schedule: List<ScheduleListItem?>?) {
        alarmReceiver.cancelAlarm(requireContext())

        if (schedule != null) {
            for (i in schedule) {
                if (i?.status == "NOT_STARTED") {
                    var formattedDate = formatDate(i.date!!)
                    alarmReceiver.setOneTimeAlarm(
                        requireContext(),
                        formattedDate,
                        i.name!!,
                        i.startTime!!
                    )
                }
            }
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

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}