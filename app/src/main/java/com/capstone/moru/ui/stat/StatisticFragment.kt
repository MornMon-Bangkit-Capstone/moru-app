package com.capstone.moru.ui.stat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.FragmentStatisticBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.schedule.ScheduleViewModel
import com.capstone.moru.ui.stat.adapter.HistoryListAdapter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class StatisticFragment : Fragment() {
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val scheduleViewModel: ScheduleViewModel by viewModels { factory }
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var totalDuration: Int = 0
    private var thisDayDuration: Int = 0
    private var sundayDuration: Int = 0
    private var mondayDuration: Int = 0
    private var tuesdayDuration: Int = 0
    private var wednesdayDuration: Int = 0
    private var thursdayDuration: Int = 0
    private var fridayDuration: Int = 0
    private var saturdayDuration: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())

        binding.rvSchedule.isNestedScrollingEnabled = false

        scheduleViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            scheduleViewModel.getCurrentSchedule(token, "")
        }

        scheduleViewModel.schedule.observe(viewLifecycleOwner) { routine ->
            resetValue()
            val filterRoutine = routine?.filter { item -> item?.status == "COMPLETED" }
            extractData(filterRoutine)

            initRecyclerView(filterRoutine)
            setupGraph()
        }

        scheduleViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        scheduleViewModel.error.observe(viewLifecycleOwner) {
            retry(it)
        }
    }

    private fun resetValue(){
        sundayDuration = 0
        tuesdayDuration = 0
        wednesdayDuration = 0
        thursdayDuration =0
        fridayDuration = 0
        saturdayDuration = 0
        mondayDuration = 0
    }

    private fun setupGraph() {
        val barArrayList = ArrayList<BarEntry>()
        barArrayList.add(BarEntry(0f, sundayDuration.toFloat()))
        barArrayList.add(BarEntry(1f, tuesdayDuration.toFloat()))
        barArrayList.add(BarEntry(2f, wednesdayDuration.toFloat()))
        barArrayList.add(BarEntry(3f, thursdayDuration.toFloat()))
        barArrayList.add(BarEntry(4f, fridayDuration.toFloat()))
        barArrayList.add(BarEntry(5f, saturdayDuration.toFloat()))
        barArrayList.add(BarEntry(6f, mondayDuration.toFloat()))

        Log.e("ARRAY", barArrayList.toString())
        val barDataSet = BarDataSet(barArrayList, "Routines Chart")
        val barData = BarData(barDataSet)
        binding.barChart.data = barData

        val daysOfWeek = arrayOf("Sun", "Tue", "Wed", "Thu", "Fri", "Sat", "Mon")
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(daysOfWeek)
        binding.barChart.description.isEnabled = false

        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.valueTextColor = Color.BLACK;
        barDataSet.valueTextSize = 14f;
        binding.barChart.setTouchEnabled(false)

        binding.barChart.setDrawGridBackground(false)
        binding.barChart.setDrawBarShadow(false)
        binding.barChart.setDrawBorders(false)

        binding.barChart.xAxis.setDrawAxisLine(false)
        binding.barChart.xAxis.setDrawGridLines(false)
        binding.barChart.axisLeft.setDrawAxisLine(false)
        binding.barChart.axisLeft.setDrawGridLines(false)
        binding.barChart.axisRight.setDrawAxisLine(false)
        binding.barChart.axisRight.setDrawGridLines(false)

        binding.barChart.axisLeft.setDrawLabels(false)
        binding.barChart.axisRight.setDrawLabels(false)

        binding.barChart.invalidate()
    }

    private fun extractData(filterRoutine: List<ScheduleListItem?>?) {
        if (filterRoutine != null) {
            for (i in filterRoutine.filter { item -> item?.date == selectedDate }) {
                thisDayDuration += i?.durationMin!!.toInt()
            }

            for (i in filterRoutine) {
                populateWeekData(getDayOfWeekFromDate(i?.date!!), i.durationMin!!)

                totalDuration += i?.durationMin!!.toInt()
            }

            binding.tvAvgToday.text = formatDuration(thisDayDuration)
            binding.tvTotalAvg.text = formatDuration(totalDuration)
        }
    }

    private fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        val hoursText = if (hours > 0) "$hours hours" else "0 hours"
        val minutesText = if (remainingMinutes > 0) "$remainingMinutes minutes" else "0 minutes"

        return if (hours > 0 && remainingMinutes > 0) {
            "$hoursText $minutesText"
        } else {
            "$hoursText $minutesText"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun initRecyclerView(schedule: List<ScheduleListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSchedule.layoutManager = layoutManager

        val adapter = HistoryListAdapter(schedule)

        binding.rvSchedule.adapter = adapter
        adapter.setOnItemClickCallback(object : HistoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineBooks: ScheduleListItem?) {

            }

        })
    }

    private fun populateWeekData(dateString: String, duration: Int) {
        Log.e("TEST", "$dateString + $duration")
        return when (dateString) {
            "Sunday" -> sundayDuration += duration
            "Monday" -> mondayDuration += duration
            "Tuesday" -> tuesdayDuration += duration
            "Wednesday" -> wednesdayDuration += duration
            "Thursday" -> thursdayDuration += duration
            "Friday" -> fridayDuration += duration
            "Saturday" -> saturdayDuration += duration
            else -> {}
        }
    }

    private fun getDayOfWeekFromDate(dateString: String): String {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = format.parse(dateString)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "Invalid Day"
        }
    }

    private fun retry(it: Boolean?) {
        if (it!!) {
            binding.progressBar.visibility = View.GONE
            binding.btnRetry.visibility = View.VISIBLE
        } else {
            binding.btnRetry.visibility = View.GONE
        }
    }
}