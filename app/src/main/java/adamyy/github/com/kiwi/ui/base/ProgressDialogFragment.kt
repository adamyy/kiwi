package adamyy.github.com.kiwi.ui.base

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.databinding.ProgressDialogBinding
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProgressDialogFragment : FullScreenDialogFragment() {

    companion object {
        val TAG = ProgressDialogFragment::class.simpleName

        private val MESSAGE_KEY = "progress_dialog_message"

        private fun newInstance(message: Int): ProgressDialogFragment {
            val fragment = ProgressDialogFragment()
            val bundle = Bundle()
            bundle.putInt(MESSAGE_KEY, message)
            fragment.arguments = bundle
            return fragment
        }

        fun show(activity: FragmentActivity, @StringRes message: Int = R.string.loading): ProgressDialogFragment {
            val dialog = newInstance(message)
            dialog.show(activity.supportFragmentManager, FullScreenDialogFragment.TAG)
            if (activity is BaseKiwiActivity<*, *, *>) activity.fullScreenDialog = dialog
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ProgressDialogBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_progress_fullscreen, container, false)
        binding.message.text = getString(arguments.getInt(MESSAGE_KEY))
        return binding.root
    }

}