package adamyy.github.com.kiwi.ui.screens.base

import adamyy.github.com.kiwi.ui.common.UIEvent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseKiwiFragment<T: ViewDataBinding> : Fragment() {

    lateinit var binding: T

    lateinit var uiEvents: Observable<UIEvent>

    val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * Add subscriptions that needs to be disposed at the end of lifecycle
     */
    fun addDisposable(@NonNull disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    abstract @LayoutRes fun getLayoutRes(): Int

    abstract fun initUi(): Unit

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}