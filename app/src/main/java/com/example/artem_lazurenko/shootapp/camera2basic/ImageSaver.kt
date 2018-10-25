package com.example.android.camera2basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.example.artem_lazurenko.shootapp.camera2basic.EXTENSION
import com.example.artem_lazurenko.shootapp.camera2basic.PIC_FILE_NAME

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

interface IRepo {

    fun get(fName: String):Image
    fun get():List<Bitmap>
    fun create(item: Image?): File
    fun delete(item: Image)
    fun prepare()
    fun release()

    companion object {
        private var instance: IRepo? = null

        fun getInstance(cntx: Context): IRepo {
            if (instance == null) instance = Repo(cntx)
            return instance!!
        }
    }

    private class Repo(cntx: Context) : IRepo {
        private val imageDir: File
        var backgroundHandler: Handler? = null

        init {
            val appname = with(cntx) {getString(applicationInfo.labelRes) }
            imageDir = with(File(cntx.filesDir, appname)) {
                if(!exists()) mkdir()
                setReadable(true, false)
                setWritable(true, false)
                setExecutable(true, false)
                return@with this
            }
        }

        override fun get(fName: String): Image {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun get(): List<Bitmap> = imageDir
                .listFiles()
                .asSequence()
                .map{
                    val options = BitmapFactory.Options();
                    options.inSampleSize = 8;
                    BitmapFactory.decodeFile(it.toString(), options)
                }.toList()

        override fun create(item: Image?): File {
            if (item != null) {
                backgroundHandler?.post(ImageSaver(item, getFile()))
            }
            return getFile()
        }

        override fun delete(item: Image) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private var backgroundThread: HandlerThread? = null

        override fun prepare() {
            backgroundThread = HandlerThread("RepoBackground").also { it.start() }
            backgroundHandler = Handler(backgroundThread?.looper)
        }

        override fun release() {
            backgroundThread?.quitSafely()
            try {
                backgroundThread?.join()
                backgroundThread = null
                backgroundHandler = null
            } catch (e: InterruptedException) {
                Log.e(Repo::class.java.canonicalName, e.toString())
            }
        }

        private fun getFile(): File {
            var suffix: String = SimpleDateFormat("yyyyMMDDHHMM").format(Date())
            var num = 0
            while(File(imageDir, PIC_FILE_NAME + suffix + EXTENSION).exists()) { suffix += num++ }
            return File(imageDir,PIC_FILE_NAME + suffix + EXTENSION)
        }

    }

    /**
     * Saves a JPEG [Image] into the specified [File].
     */
    private class ImageSaver(
            /**
             * The JPEG image
             */
            private val image: Image,

            /**
             * The file we save the image into.
             */
            private val file: File
    ) : Runnable {

        override fun run() {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            var output: FileOutputStream? = null
            try {
                output = FileOutputStream(file).apply {
                    write(bytes)
                }
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            } finally {
                image.close()
                output?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        Log.e(TAG, e.toString())
                    }
                }
            }
        }

        companion object {
            /**
             * Tag for the [Log].
             */
            private val TAG = "ImageSaver"
        }
    }
}

