package com.example.artem_lazurenko.shootapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), IMainView {
    override fun showNoCameraDevice() {
        getDialogFragment(R.string.request_permission
                , { _, _ -> this@MainActivity.finish()}, null)
                .show(fragmentManager, null)
    }

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    /** Check if this device has a camera */
    override fun isCameraPresent(): Boolean = this.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

    override fun checkCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            getDialogFragment(R.string.request_permission,
                    { _, _ ->  requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION) },
                    //finish activity as for now we consider we can't work without camera
                    { _, _ ->  this@MainActivity.finish()})
                    .show(fragmentManager, null)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                getDialogFragment(R.string.request_permission, { _, _ ->  this@MainActivity.finish()}, null)                         //finish activity as for now we consider we can't work without camera
                    .show(fragmentManager, null)
            else presenter.showMainScreen()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun showMainScreen() {
        supportFragmentManager.inTransaction {
            add(R.id.container, MainFragment())
        }
    }
}
