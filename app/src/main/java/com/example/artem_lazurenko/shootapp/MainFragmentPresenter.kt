package com.example.artem_lazurenko.shootapp

import android.media.Image
import android.util.Log
import com.example.android.camera2basic.IRepo
import java.io.File

class MainFragmentPresenter(val view: IMainFragmentView, val repo:IRepo): IPresenter {
    fun saveImage(nextImage: Image?): File {
        val file = repo.create(nextImage)
        view.showToast("Saved: $file")
        Log.d(TAG, file.toString())
        return file
    }

    override fun start() {
        repo.prepare()
    }

    override fun stop() {
        repo.release()
    }

    companion object {
        val TAG = MainFragmentPresenter::class.java.canonicalName
    }
}
