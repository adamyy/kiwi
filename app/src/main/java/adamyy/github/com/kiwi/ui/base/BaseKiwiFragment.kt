package adamyy.github.com.kiwi.ui.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseKiwiFragment<T: ViewDataBinding> : Fragment() {

    protected lateinit var binding: T

    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    /**
     * Add subscriptions that needs to be disposed at the end of lifecycle
     */
    fun addDisposable(@NonNull disposable: Disposable) {
        disposables.add(disposable)
    }

    abstract @LayoutRes fun getLayoutRes(): Int

    abstract fun initUi(): Unit

}