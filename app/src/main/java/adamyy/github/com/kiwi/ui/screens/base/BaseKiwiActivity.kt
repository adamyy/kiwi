package adamyy.github.com.kiwi.ui.screens.base

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.common.snack
import android.arch.lifecycle.LifecycleActivity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar

abstract class BaseKiwiActivity<T: ViewDataBinding>: LifecycleActivity() {

    var fullScreenDialog: FullScreenDialogFragment? = null

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<T>(this, getLayoutRes())
        initUi()
    }

    override fun onResume() {
        super.onResume()
        restoreFullScreenDialog()
    }

    fun setFullScreenProgressDialog(@StringRes message: Int = R.string.loading) {
        fullScreenDialog = ProgressDialogFragment.show(this, message)
    }

    fun restoreFullScreenDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(FullScreenDialogFragment.TAG)
        if (dialog is FullScreenDialogFragment) fullScreenDialog = dialog
    }

    fun dismissFullScreenDialog() = fullScreenDialog?.dismiss()

    fun snackMessage(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(message, length, f)
    }

    fun snackMessage(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(messageRes, length, f)
    }

    open @LayoutRes fun getLayoutRes(): Int = R.layout.activity_basic

    /**
     * Called in
     */
    abstract fun initUi(): Unit
}
