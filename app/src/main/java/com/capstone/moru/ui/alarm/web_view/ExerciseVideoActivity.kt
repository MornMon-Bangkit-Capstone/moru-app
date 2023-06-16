package com.capstone.moru.ui.alarm.web_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityExerciseVideoBinding

class ExerciseVideoActivity : AppCompatActivity() {
    private var _binding: ActivityExerciseVideoBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExerciseVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var link = intent.getStringExtra(LINK_KEY)
        binding.webView.settings.javaScriptEnabled = true
        Log.e("LINK", link.toString())

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Toast.makeText(this@ExerciseVideoActivity, "Your exercise routine is ready", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.webView.loadUrl("$link")
    }

    companion object {
        const val LINK_KEY = "link_key"
    }
}