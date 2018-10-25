package com.example.artem_lazurenko.shootapp

import android.app.Dialog
import android.os.Bundle
import android.app.DialogFragment
import android.content.DialogInterface
import android.support.annotation.StringRes
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.widget.Toast

val FILENAME = "image.jpg"

fun FragmentActivity.showToast(text: String) {
    runOnUiThread { Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
}

fun getDialogFragment(@StringRes messageId: Int,
                             positiveAction: (DialogInterface?, Int) -> Unit,
                             negativeAction: ((DialogInterface?, Int) -> Unit)?): DialogFragment {

    class ShootAppDialogFragment(): DialogFragment() {
        @StringRes var messageId: Int = 0
        lateinit var positiveAction: (p0: DialogInterface?, p1: Int) -> Unit
        var negativeAction: ((DialogInterface?, Int) -> Unit)? = null

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(activity).apply {
                setMessage(messageId)
                setPositiveButton(android.R.string.ok, positiveAction)/* { _, _ ->
                        parentFragment.requestPermissions(arrayOf(Manifest.permission.CAMERA),
                                REQUEST_CAMERA_PERMISSION
                    })*/
                if(negativeAction != null) setNegativeButton(android.R.string.cancel, negativeAction) /*{ _, _ ->
                        parentFragment.activity?.finish()
                    }*/
            }.create()
    }
    return ShootAppDialogFragment().apply {
        this.messageId = messageId
        this.positiveAction = positiveAction
        this.negativeAction = negativeAction
    }
}

val REQUEST_CAMERA_PERMISSION = 1

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        func()
        commit()
    }
}