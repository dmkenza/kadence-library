package com.kadencelibrary.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable

open class KadenceBottomSheetDialogFragment : BottomSheetDialogFragment() {


//    override fun setupDialog(dialog: Dialog, style: Int) {
//        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        super.setupDialog(dialog, style)
//
////        val contentView = View.inflate(context, R.layout.fragment_image_source_select, null)
////        dialog.setContentView(contentView)
////
////        dialog.findViewById<View>(R.id.textChooseCamera)?.setOnClickListener {
////            onCameraClickListener?.invoke()
////            dismiss()
////        }
////
////        dialog.findViewById<View>(R.id.textChooseGallery).setOnClickListener {
////            onGalleryClickListener?.invoke()
////            dismiss()
////        }
//    }

    val disposables = CompositeDisposable()


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialogInterface ->
            val dialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from(bottomSheet).isHideable = true
            }
        }


        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.setBackgroundColor(Color.TRANSPARENT)

        return bottomSheetDialog
    }

    override fun onDestroy() {
        disposables.dispose()
        disposables.clear()
        super.onDestroy()
    }

    fun dismissIfAdded() {

        kotlin.runCatching {
            if (isVisible && isAdded) {
                dismiss()
            }
        }

    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        val dialog = super.onCreateDialog(savedInstanceState)
//
//        return dialog
//    }


}