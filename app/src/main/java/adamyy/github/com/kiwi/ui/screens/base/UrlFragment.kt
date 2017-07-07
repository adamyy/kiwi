package adamyy.github.com.kiwi.ui.screens.base

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.databinding.UrlFragmentBinding
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.*

abstract class UrlFragment: BaseKiwiFragment<UrlFragmentBinding>() {

    companion object {
        val TAG = UrlFragment::class.simpleName
        const val TIMEOUT_LIMIT = 30000L
    }

    private val timeout: Runnable = Runnable {
        binding.webView.removeCallbacks(loadUrl)
        onLoadingError()
    }

    private lateinit var loadUrl: Runnable

    override fun initUi() {
        setup(binding.webView)
    }

    private fun setup(webView: WebView) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        webView.setWebViewClient(object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading $url")
                return onOverrideUrlLoading(view, Uri.parse(url))
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading ${request.url}")
                return onOverrideUrlLoading(view, request.url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(TAG, "onPageFinished $url")
                view?.removeCallbacks(timeout)
                onUrlLoaded(url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Log.d(TAG, "onReceivedError ${request?.url.toString()} ${error.toString()}")
                view?.removeCallbacks(timeout)
                onLoadingError()
            }
        })
    }

    protected fun loadUrlWithTimeout(url: String) {
        loadUrl = Runnable {
            binding.webView.loadUrl(url)
        }
        binding.webView.post(loadUrl)
        binding.webView.postDelayed(timeout, TIMEOUT_LIMIT)
    }

    protected fun showLoading(show: Boolean) {
        binding.loadingContainer.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        binding.webView.destroy()
        super.onDestroy()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_url

    abstract fun onOverrideUrlLoading(view: WebView, url: Uri): Boolean

    abstract fun onUrlLoaded(url: String?): Unit

    abstract fun onLoadingError(): Unit
}