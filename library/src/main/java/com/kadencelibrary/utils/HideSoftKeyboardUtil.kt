package com.kadencelibrary.utils

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatDialog
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Allow hide keyboard just tap outside content.
 */

object HideSoftKeyboardUtil {

    fun enableDisableViewGroup(viewGroup: ViewGroup, enabled: Boolean) {
        val childCount = viewGroup.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            view.isEnabled = enabled
            if (view is ViewGroup) {
                enableDisableViewGroup(view, enabled)
            }
        }
    }


    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun softHide(fragment: androidx.fragment.app.Fragment) {
        try {
            val inputMethodManager = fragment.context?.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                fragment.activity?.currentFocus?.windowToken, 0
            )
        } catch (e: Exception) {
        }

    }


    fun softHide(view: View, fragment: androidx.fragment.app.Fragment) {
        try {
            if (view !is EditText) {
                view.setOnTouchListener { _, _ ->
                    softHide(fragment)
                    false
                }
            }
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    softHide(innerView, fragment)
                }
            }
        } catch (e: Exception) {
        }
    }

    /**
     * For fialog
     */
    fun softHide(dialog: AppCompatDialog) {

        try {
            val inputMethodManager = dialog.context.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(
                dialog.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
        }

    }

    fun softHide(dialog: Dialog) {

        try {
            val inputMethodManager = dialog.context.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(
                dialog.currentFocus!!.windowToken, 0
            )
        } catch (e: Exception) {
        }

    }


    fun softHide(view: View, dialog: Dialog) {
        try {
            if (view !is EditText) {
                view.setOnTouchListener { v, _ ->
                    v.performClick();
                    softHide(dialog)
                    false
                }
            }
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    softHide(innerView, dialog)
                }
            }
        } catch (e: Exception) {
        }
    }


    fun softHide(view: View, dialog: AppCompatDialog) {
        try {
            if (view !is EditText) {
                view.setOnTouchListener { v, _ ->
                    v.performClick()
                    softHide(dialog)
                    false
                }
            }
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    softHide(innerView, dialog)
                }
            }
        } catch (e: Exception) {
        }
    }


    /**
     * For Activity
     */
    fun softHide(view: View, activity: Activity) {
        try {
            if (view !is EditText) {
                view.setOnTouchListener { v, _ ->
                    v.performClick();
                    try {
                        softHide(activity)

                    } catch (e: Exception) {
                    }

                    false
                }
            }
            //If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    softHide(innerView, activity)
                }
            }
        } catch (e: Exception) {
        }

    }

    fun softHide(activity: Activity) {

        try {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )

        } catch (e: Exception) {
        }

    }


}
