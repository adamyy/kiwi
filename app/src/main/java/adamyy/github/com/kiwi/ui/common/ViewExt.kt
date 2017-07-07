package adamyy.github.com.kiwi.ui.common

import adamyy.github.com.kiwi.R
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View

inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(@StringRes actionRes: Int, @ColorRes background: Int = R.color.colorAccent, listener: (View) -> Unit = {}) {
    action(view.resources.getString(actionRes), background, listener)
}

fun Snackbar.action(action: String, @ColorRes background: Int = R.color.colorAccent, listener: (View) -> Unit = {}) {
    view.setBackgroundColor(ContextCompat.getColor(context, background))
    setActionTextColor(ContextCompat.getColor(context, R.color.white))
    setAction(action, listener)
}