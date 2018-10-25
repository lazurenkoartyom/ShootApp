package com.example.artem_lazurenko.shootapp

class MainPresenter(val view: IMainView) : IPresenter {
    fun showMainScreen() {
        view.showMainScreen()
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        if (view.isCameraPresent())
            view.checkCameraPermission()
        else view.showNoCameraDevice()
    }
}
