package com.capstone.moru.ui.customview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.capstone.moru.R
import com.capstone.moru.ui.auth.login.LoginActivity
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.profile.ProfileViewModel

class LogoutDialog : DialogFragment() {

    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        factory = ViewModelFactory.getInstance(requireContext())
        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.TransparentDialogTheme)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_logout, null)
            builder.setView(view)
            builder.setCancelable(false)

            val noButton = view?.findViewById<Button>(R.id.btn_no)
            val yesButton = view?.findViewById<Button>(R.id.btn_yes)

            noButton?.setOnClickListener {
                dismiss()
            }

            yesButton?.setOnClickListener {
                profileViewModel.logout()

                val intentToLogin = Intent(requireContext(), LoginActivity::class.java)
                intentToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intentToLogin)
                activity?.finish()
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }
}