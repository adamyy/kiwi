package adamyy.github.com.kiwi.ui.features.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.network.UserApi
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimelineFragment : BaseKiwiFragment<TimelineBinding>() {

    companion object {
        val TAG = TimelineFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    @Inject lateinit var userApi: UserApi

    override fun initUi() {
        userApi.verifyCredentials()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.d(TAG, result.toString())
                            binding.textView.text = "Welcome! ${result.name}"
                        },
                        { error ->
                            error.printStackTrace()
                        },
                        {
                            Log.d(TAG, "Completed")
                        }
                )
    }

}