package com.capstone.moru.ui.add_routine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.commit
import com.capstone.moru.R
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineFragment

class AddRoutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        setupView()

        supportFragmentManager.commit {
            replace(R.id.placeholder, PickRoutineFragment(), PickRoutineFragment::class.java.simpleName)
        }

    }
    private fun setupView() {
        supportActionBar?.hide()
    }
}