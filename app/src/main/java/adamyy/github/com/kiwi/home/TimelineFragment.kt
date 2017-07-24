package adamyy.github.com.kiwi.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.repository.UserRepository
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import adamyy.github.com.kiwi.ui.common.SingleUIModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimelineFragment : BaseKiwiFragment<TimelineBinding>() {

    companion object {
        val TAG = TimelineFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    @Inject lateinit var userRepo: UserRepository

    override fun onResume() {
        super.onResume()
        showUserInfo()
    }

    private fun showUserInfo() {
        userRepo.verifyCredentials()
                .map { (inFlight, isSuccess, data, error) ->
                    if (inFlight) SingleUIModel.inProgress()
                    else if (isSuccess) SingleUIModel.success(data)
                    else SingleUIModel.failure(error!!)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (inFlight, isSuccess, data, error) ->
                            Log.d(TAG, data.toString())
                            binding.textView.text = "Welcome! ${data?.name}"
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