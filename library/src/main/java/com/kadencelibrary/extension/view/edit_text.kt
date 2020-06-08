package com.kadencelibrary.extension.view

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/** set focus on field  */

fun EditText.requestFocusAndEdit() {
    this.requestFocus()
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    setSelection(this.getText().length);
}

/** set edit cursor to end of editable field  */

fun EditText.setCursorToEnd() {
    setSelection(this.getText().length);
}
