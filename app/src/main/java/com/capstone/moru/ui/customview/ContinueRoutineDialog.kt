package com.capstone.moru.ui.customview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.capstone.moru.R
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.alarm.AlarmViewModel
import com.capstone.moru.ui.factory.ViewModelFactory

class ContinueRoutineDialog(token: String, id: Int) : DialogFragment() {
    private lateinit var factory: ViewModelFactory
    private val alarmViewModel: AlarmViewModel by viewModels { factory }
    var saveToken = token
    var saveId = id

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        factory = ViewModelFactory.getInstance(requireContext())

        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.TransparentDialogTheme)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_continue_routine, null)
            builder.setView(view)
            builder.setCancelable(false)

            val noButton = view?.findViewById<Button>(R.id.btn_no)
            val yesButton = view?.findViewById<Button>(R.id.btn_yes)

            noButton?.setOnClickListener {
                alarmViewModel.updateScheduleAfterRoutine(saveToken, saveId, "SKIPPED", 0)

                val intentToMain = Intent(requireContext(), MainActivity::class.java)
                intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentToMain)
                activity?.finish()
            }

            yesButton?.setOnClickListener {
                dismiss()
            }

            builder.create()
        }
    }
}