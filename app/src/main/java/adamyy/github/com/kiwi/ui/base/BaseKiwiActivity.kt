package adamyy.github.com.kiwi.ui.base

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.common.snack
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

abstract class BaseKiwiActivity<T: ViewDataBinding>: AppCompatActivity() {

    var fullScreenDialog: FullScreenDialogFragment? = null

    lateinit var binding: T

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<T>(this, getLayoutRes())
    }

    @CallSuper override fun onResume() {
        super.onResume()
        restoreFullScreenDialog()
    }

    protected fun setFullScreenProgressDialog(@StringRes message: Int = R.string.loading) {
        fullScreenDialog = ProgressDialogFragment.show(this, message)
    }

    protected fun restoreFullScreenDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(FullScreenDialogFragment.TAG)
        if (dialog is FullScreenDialogFragment) fullScreenDialog = dialog
    }

    protected fun dismissFullScreenDialog() = fullScreenDialog?.dismiss()

    protected fun snackMessage(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(message, length, f)
    }

    protected fun snackMessage(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(messageRes, length, f)
    }

    open @LayoutRes fun getLayoutRes(): Int = R.layout.activity_basic
}
