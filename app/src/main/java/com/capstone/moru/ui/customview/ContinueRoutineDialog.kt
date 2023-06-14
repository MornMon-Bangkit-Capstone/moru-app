package com.capstone.moru.ui.customview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.capstone.moru.R
import com.capstone.moru.ui.MainActivity

class ContinueRoutineDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.TransparentDialogTheme)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_continue_routine, null)
            builder.setView(view)
            builder.setCancelable(false)

            val noButton = view?.findViewById<Button>(R.id.btn_no)
            val yesButton = view?.findViewById<Button>(R.id.btn_yes)

            noButton?.setOnClickListener {
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