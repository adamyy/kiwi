package adamyy.github.com.kiwi.ui.screens.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Window

abstract class FullScreenDialogFragment: DialogFragment() {

    companion object {
        val TAG = FullScreenDialogFragment::class.simpleName
    }

    protected var isActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        isActive = true
        return dialog
    }

    override fun dismiss() {
        super.dismiss()
        isActive = false
    }

    fun isShowing(): Boolean = isActive

}