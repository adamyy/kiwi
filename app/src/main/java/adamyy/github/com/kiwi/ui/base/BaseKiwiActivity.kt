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
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseKiwiActivity<in V : BaseView, P : BasePresenter<V>, B : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector, BaseView {

    @Inject protected lateinit var presenter: P

    protected var fullScreenDialog: FullScreenDialogFragment? = null

    protected lateinit var binding: B

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView<B>(this, getLayoutRes())
        presenter.bindView(this as V)
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

    protected fun dismissFullScreenDialog() {
        fullScreenDialog?.dismiss()
    }

    protected fun snackMessage(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(message, length, f)
    }

    protected fun snackMessage(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit = {}) {
        binding.root.snack(messageRes, length, f)
    }

    open @LayoutRes fun getLayoutRes(): Int = R.layout.activity_basic
}
