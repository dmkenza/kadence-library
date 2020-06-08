package com.kadencelibrary.extension.text

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.math.RoundingMode
import java.net.URL
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun Any.toJson(): String? = Gson().toJson(this)


/** convert time to format for srt subtitles */

fun Double.toSrtTimeFormat(): String {
    val rem = this.rem(1) * 1000


    val s = this % 60
    val m = (this / (60) % 60)
    val h = (this / (60 * 60) % 24)

    val ss = rem.toDouble()


    val mFormat = DecimalFormat("00")
    mFormat.setRoundingMode(RoundingMode.DOWN)
    val date = mFormat.format(java.lang.Double.valueOf(h)) + ":" + mFormat.format(
        java.lang.Double.valueOf(m)
    ) +
            ":" + mFormat.format(java.lang.Double.valueOf(s)) + "," + mFormat.format(
        java.lang.Double.valueOf(
            ss
        )
    )

    return date

}


fun String.deleteQuotes(): String = this.replace(")", "").replace("(", "")

fun <T> String.toObject(clazz: Class<T>): T? = Gson().fromJson(this, clazz)


/**
 * Upper first element of string
 */
fun String.toUpperCaseOnce(): String =
    Character.toUpperCase(this[0]) + this.substring(1).toLowerCase()


/**
 * Text change listener short way
 */

fun EditText.onTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}


/**
 * Return base URL from string, empty from wrong URL.
 */

fun String.getBaseUrl(): String {

    var baseUrl = ""
    try {
        val url = URL(this);
        baseUrl = url.getProtocol() + "://" + url.getHost();

    } catch (e: Exception) {

    }


    return baseUrl

}




/**
 * Gson converter helpers
 */

inline fun <reified T> Gson.fromJson(json: String?) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJson(json: Reader?) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJson(json: JsonElement?) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)


/**
 * Convert milliseconds to Text by pattern of Timestamp.
 * @param pattern - Timestamp pattern for example  hh:mm:ss
 */


fun Long.getTimestamp(pattern: String = ""): String {
    var pattern = pattern

    val tz = TimeZone.getTimeZone("UTC")

    if (pattern.isEmpty()) {
        pattern = "HH:mm:ss"
    }
    val df = SimpleDateFormat(pattern)
    df.setTimeZone(tz)
    return df.format(Date(this))
}


/** Encode url string for http  request*/

fun String.encodeForHttpRequest(code: String = "UTF-8"): String {
    return URLEncoder.encode(this, code)
}


fun String.convertToHTMLText() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
} else {
    Html.fromHtml(this)
}

fun String.setDecimalFormat(): DecimalFormat {
    return when (this.length) {
        0 -> DecimalFormat("#.####")
        1 -> DecimalFormat("#.###")
        2 -> DecimalFormat("#.##")
        3 -> DecimalFormat("#.#")
        else -> DecimalFormat("#")
    }
}


private val CYRILLIC = Pattern.compile("[Ѐ-ӿ]").toRegex()
private val GREEK = Pattern.compile("[Ͱ-Ͽ]").toRegex()

/** Detect latin letters in text*/

fun isOnlyLatinLetters(string: String?): Boolean {
    if (string == null) return false
    return !(CYRILLIC.containsMatchIn(string)
            || GREEK.containsMatchIn(string))
}


/** @return return text with html tag. */

fun getColoredHTMLText(text: String, color: String): String {
    val sb = StringBuilder()
    val cl1 = "#" + color.substring(3, color.length)
    sb.append(" <font color='${cl1}'> ${text}</font>  ")
    return sb.toString()
}

