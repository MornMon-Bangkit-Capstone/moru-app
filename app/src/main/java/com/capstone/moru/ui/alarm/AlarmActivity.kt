package com.capstone.moru.ui.alarm

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.data.api.response.ScheduleDetailListItem
import com.capstone.moru.databinding.ActivityAlarmBinding
import com.capstone.moru.ui.customview.CompleteRoutineDialog
import com.capstone.moru.ui.customview.ContinueRoutineDialog
import com.capstone.moru.ui.customview.FinishRoutineDialog
import com.capstone.moru.ui.factory.ViewModelFactory
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlarmActivity : AppCompatActivity() {
    private var _binding: ActivityAlarmBinding? = null
    private val binding get() = _binding!!

    private var formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private var formatterDay: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")

    private lateinit var factory: ViewModelFactory
    private val alarmViewModel: AlarmViewModel by viewModels { factory }

    private var selectedTime: String? = LocalTime.now().format(formatterTime)
    private var selectedDate: String? = LocalDate.now().format(formatterDay)
    private var saveToken: String? = null
    private var saveScheduleId: Int? = null
    private var duration: Int? = null
    private var timer: CountDownTimer? = null
    private var isTimerRunning: Boolean = false
    private var elapsedTimeMinutes: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAlarmBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)
        setupView()

        alarmViewModel.getUserToken().observe(this) { token ->
            saveToken = token
            alarmViewModel.getCurrentSchedule(token, selectedDate!!)
        }

        alarmViewModel.schedule.observe(this) { routine ->
            if (routine != null) {
                for (i in routine) {
                    var startTime = LocalTime.parse(i?.startTime)
                    var endTime = LocalTime.parse(i?.endTime)
                    var checkTime = LocalTime.parse(selectedTime)

                    if ((checkTime.isAfter(startTime) && checkTime.isBefore(endTime)) || (startTime == checkTime)) {
                        duration = Duration.between(startTime, endTime).toMinutes().toInt()
                        saveScheduleId = i?.refId

                        alarmViewModel.getUserScheduleDetail(saveToken!!, i?.id!!)
                        if (saveScheduleId!! > 100) {
                            alarmViewModel.getBookRoutineDetail(saveToken!!, saveScheduleId!!, 1)
                        } else {
                            alarmViewModel.getExerciseRoutineDetail(
                                saveToken!!,
                                saveScheduleId!!,
                                1
                            )
                        }
                    }
                }
            }
        }

        alarmViewModel.scheduleDetail.observe(this) { schedule ->
            initScheduleDetail(schedule)
        }

        alarmViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.backBtnAlarm.setOnClickListener {
            val continueRoutineDialog = ContinueRoutineDialog()
            continueRoutineDialog.show(supportFragmentManager, "DialogContinue")
        }

        binding.btnFinish.setOnClickListener {
            val finishRoutineDialog = FinishRoutineDialog()
            finishRoutineDialog.show(supportFragmentManager, "DialogFinish")
        }
    }

    private fun initScheduleDetail(schedule: List<ScheduleDetailListItem?>?) {
        val scheduleItem = schedule?.get(0)

        binding.tvRoutineNameAlarm.text = scheduleItem?.name
        startCountdownTimer(duration!!)
    }

    private fun startCountdownTimer(duration: Int) {
        if (!isTimerRunning) {
            val totalMinutes = duration.toLong()
            val totalMilliseconds =
                totalMinutes * 60 * 1000L
            timer =
                object : CountDownTimer(totalMilliseconds - elapsedTimeMinutes * 60 * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        elapsedTimeMinutes =
                            (totalMilliseconds - millisUntilFinished) / (60 * 1000)
                        val minutes = millisUntilFinished / 1000 / 60
                        val seconds = (millisUntilFinished / 1000) % 60
                        val countdownText = String.format("%02d : %02d", minutes, seconds)
                        binding.tvTimer.text = countdownText
                    }

                    override fun onFinish() {
                        binding.tvTimer.text = "00 : 00"

                        val finishDialog = CompleteRoutineDialog()
                        finishDialog.show(supportFragmentManager, "DialogComplete")
                        isTimerRunning = false
                    }
                }
            timer?.start() // Start the countdown timer
            isTimerRunning = true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun stopTimer() {
        timer?.cancel()
        isTimerRunning = false
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}