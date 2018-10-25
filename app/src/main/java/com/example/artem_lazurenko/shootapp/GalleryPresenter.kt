package com.example.artem_lazurenko.shootapp

import com.example.android.camera2basic.IRepo

class GalleryPresenter(val view: IGalleryFragmentView, val repo: IRepo): IPresenter {
    fun getData() =  view.displayPictures(repo.get())

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
