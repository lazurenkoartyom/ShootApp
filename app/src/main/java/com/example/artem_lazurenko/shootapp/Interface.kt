package com.example.artem_lazurenko.shootapp

import android.graphics.Bitmap

interface IPresenter {
    fun start()
    fun stop()
}

interface IMainView {
    fun isCameraPresent(): Boolean
    fun checkCameraPermission()
    fun showNoCameraDevice()
    fun showMainScreen()
}

interface IGalleryFragmentView {
    fun displayPictures(pics: List<Bitmap>)
}

interface IMainFragmentView {
    fun showToast(message: String)
}
