package adamyy.github.com.kiwi.ui.screens.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.network.UserApi
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.data.source.preferences.KiwiPreferences
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.screens.base.BaseKiwiFragment
import android.util.Log
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TimelineFragment : BaseKiwiFragment<TimelineBinding>() {

    companion object {
        val TAG = TimelineFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    val prefs: AuthPref by lazy { KiwiPreferences(context) }
    val userApi: UserApi by lazy { UserApi.create(prefs.getAccessToken()!!) }

    override fun initUi() {
        userApi.verifyCredentials()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.d(TAG, result.toString())
                            loadImage(result.profileImageUrl)
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

    private fun loadImage(url: String) {
        Glide.with(this)
                .load(url)
                .into(binding.imageView)
    }


}