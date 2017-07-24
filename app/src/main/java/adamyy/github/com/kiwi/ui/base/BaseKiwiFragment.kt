package adamyy.github.com.kiwi.ui.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseKiwiFragment<T: ViewDataBinding> : Fragment() {

    protected lateinit var binding: T

    private val disposables: CompositeDisposable = CompositeDisposable()

    @CallSuper override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    @CallSuper override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    @CallSuper override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    abstract @LayoutRes fun getLayoutRes(): Int

    protected fun getAppCompatActivity(): AppCompatActivity = activity as AppCompatActivity

    /**
     * Attach subscription to the lifecycle of this fragment so it will be destroyed in onDestroy
     */
    protected fun Disposable.attach(): Boolean = disposables.add(this)
}