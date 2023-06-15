package com.capstone.moru.ui.alarm.web_view

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityBookViewBinding

class BookViewActivity : AppCompatActivity() {
    private var _binding: ActivityBookViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBookViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val iSBN = intent.getIntExtra(LINK_KEY, 0)
        Log.e("TEST", iSBN.toString())
        binding.webView.settings.javaScriptEnabled = true
        val html = "<!DOCTYPE html \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "  \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "  <head>\n" +
                "    <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n" +
                "    <title>Google Books Embedded Viewer API Example</title>\n" +
                "    <script type=\"text/javascript\" src=\"https://www.google.com/books/jsapi.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "      google.books.load();\n" +
                "\n" +
                "      function initialize() {\n" +
                "        var viewer = new google.books.DefaultViewer(document.getElementById('viewerCanvas'));\n" +
                "        viewer.load('ISBN:$iSBN');\n" +
                "        viewer.resize();\n" +
                "      }\n" +
                "\n" +
                "      google.books.setOnLoadCallback(initialize);\n" +
                "      \n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"viewerCanvas\" style=\" height: 500px\"></div>\n" +
                "  </body>\n" +
                "</html>"

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                Toast.makeText(
                    this@BookViewActivity,
                    "Your book routine is ready",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }


        binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    companion object {
        const val LINK_KEY = "link_key"
    }
}