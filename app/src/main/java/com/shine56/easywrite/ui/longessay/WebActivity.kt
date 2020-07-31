package com.shine56.easywrite.ui.longessay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shine56.easywrite.R
import com.shine56.easywrite.base.BaseActivity

class WebActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)


        resetStatusBar(TRANSPARENT_BLACK)

        val webView  = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled  = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://wapbaike.baidu.com/item/%E4%B8%89%E6%B0%91%E4%B8%BB%E4%B9%89?timestamp=1595999078459")
    }
}