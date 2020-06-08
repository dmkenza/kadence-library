package com.kadencelibrary.extension.context

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kadencelibrary.utils.HideSoftKeyboardUtil


/** KeyBoard functions. */

fun hideKeyBoard(context: Context) {
    val act = (context as Activity)
    HideSoftKeyboardUtil.hideKeyboard(act)
    act.window.decorView.clearFocus()
}


fun showKeyBoard(context: Context, edit: EditText) {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.showSoftInput(edit, 0)
}


/** Xiomi helpers. */

fun isXiomi(): Boolean {
    val manufacturer = "xiaomi"
    return manufacturer.equals(android.os.Build.MANUFACTURER, ignoreCase = true)
}

fun isMIUI(ctx: Context): Boolean {
    return (isIntentResolved(
        ctx,
        Intent("miui.intent.action.OP_AUTO_START").addCategory(Intent.CATEGORY_DEFAULT)
    )
            || isIntentResolved(
        ctx,
        Intent().setComponent(
            ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
        )
    )
            || isIntentResolved(
        ctx,
        Intent("miui.intent.action.POWER_HIDE_MODE_APP_LIST").addCategory(Intent.CATEGORY_DEFAULT)
    )
            || isIntentResolved(
        ctx,
        Intent().setComponent(
            ComponentName(
                "com.miui.securitycenter",
                "com.miui.powercenter.PowerSettings"
            )
        )
    ))
}


private fun isIntentResolved(ctx: Context, intent: Intent?): Boolean {
    return intent != null && ctx.packageManager.resolveActivity(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    ) != null
}


/** Check orientation of phone. */

fun Context.isPortraitOrientation(): Boolean {
    val orientation: Int = getResources().getConfiguration().orientation
    return orientation != Configuration.ORIENTATION_LANDSCAPE
}


fun Context.getApplicationName(): String {
    val applicationInfo = this.applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else this.getString(
        stringId
    )
}


fun Context.isDarkTheme(): Boolean {
    return this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}


/** Start service intent */
fun Context.startCompactService(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent);
    } else {
        startService(intent);
    }
}


fun Context.getColorByResId(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}


fun <T> Context.showActivity(clazz: Class<T>) {
    val intent = Intent(this, clazz)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    this.startActivity(intent)

}


fun Context.toActivity(): AppCompatActivity? {

    if (this == null)
        return null
    else if (this is AppCompatActivity)
        return this as AppCompatActivity?
    else if (this is ContextWrapper)
        return ((this as ContextWrapper).baseContext.toActivity())

    return null
}
