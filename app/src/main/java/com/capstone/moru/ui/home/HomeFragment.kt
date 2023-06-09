package com.capstone.moru.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.databinding.FragmentHomeBinding
import com.capstone.moru.ui.alarm.receiver.AlarmReceiver
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.BooksRoutineListAdapter
import com.capstone.moru.ui.routines.adapter.ExerciseRoutineListAdapter
import com.capstone.moru.ui.schedule.adapter.ScheduleListAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }

    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var emptyListRoutine: List<ScheduleListItem?>? = null
    private lateinit var alarmReceiver: AlarmReceiver
    private var flag: Boolean? = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireContext())
        alarmReceiver = AlarmReceiver()

        setupRecyclerView()
        homeViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            homeViewModel.getCurrentSchedule(token, selectedDate!!)
            homeViewModel.getBookRecommendation(token)
            homeViewModel.getExerciseRecommendation(token)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.isLoading1.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.schedule.observe(viewLifecycleOwner) { schedule ->
//            refreshAlarm(schedule)
            initRecyclerView(schedule)
        }

        homeViewModel.error.observe(viewLifecycleOwner) {
            noSchedule(it)
        }

        homeViewModel.getUsername().observe(viewLifecycleOwner) {
            binding.textView4.text = it
        }

        homeViewModel.getImageUser().observe(viewLifecycleOwner){
            Log.e("IMAGE", it)
            Glide.with(this).load(it).into(binding.ivHeaderProfile)
        }


        homeViewModel.bookRoutine.observe(viewLifecycleOwner) { book ->
            initRecyclerViewBook(book)
            flag = (book.isNullOrEmpty())

            Log.e("FLAG", flag.toString())
        }

        homeViewModel.exerciseRoutine.observe(viewLifecycleOwner) { exercise ->
            initRecyclerViewExercise(exercise)
            flag = (exercise.isNullOrEmpty())

            Log.e("FLAG", flag.toString())

        }

        homeViewModel.error1.observe(viewLifecycleOwner) {
            retryButton(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvSchedule.isNestedScrollingEnabled = false
        binding.rvRecomBook.isNestedScrollingEnabled = false
        binding.rvRecomExercise.isNestedScrollingEnabled = false
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

    private fun initRecyclerViewBook(routine: List<com.capstone.moru.data.api.response.BookListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecomBook.layoutManager = layoutManager

        val adapter = BooksRoutineListAdapter(routine)
        binding.rvRecomBook.adapter = adapter
        adapter.setOnItemClickCallback(object : BooksRoutineListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineBooks: com.capstone.moru.data.api.response.BookListItem?) {
            }
        })
    }

    private fun initRecyclerViewExercise(routine: List<com.capstone.moru.data.api.response.ExerciseListItem?>?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecomExercise.layoutManager = layoutManager

        val adapter = ExerciseRoutineListAdapter(routine)
        binding.rvRecomExercise.adapter = adapter

        adapter.setOnItemClickCallback(object : ExerciseRoutineListAdapter.OnItemClickCallback {
            override fun onItemClicked(routineExercise: com.capstone.moru.data.api.response.ExerciseListItem?) {
            }
        })
    }

    private fun noSchedule(it: Boolean?) {
        if (it!!) {
            Log.e("HOME", "ERROR")
            binding.btnRetry.visibility = View.VISIBLE

            binding.progressBar.visibility = View.GONE
            initRecyclerView(emptyListRoutine)
        } else {
            Log.e("HOME", "KAGA ERROR")
            binding.btnRetry.visibility = View.GONE
        }
    }

    private fun retryButton(it: Boolean?) {
        if (it!!) {
            binding.progressBar2.visibility = View.GONE
            if (flag == true){
                binding.btnRetry2.apply {
                    visibility = View.VISIBLE
                    isEnabled = true
                    setOnClickListener {
                        homeViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                            homeViewModel.getBookRecommendation(token)
                            homeViewModel.getExerciseRecommendation(token)
                        }
                        visibility = View.GONE
                        isEnabled = false
                    }
                }
            }
        } else {
            binding.btnRetry2.apply {
                visibility = View.GONE
                isEnabled = false
            }
        }
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("d-M-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-M-d", Locale.getDefault())

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}