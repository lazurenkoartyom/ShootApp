package com.example.artem_lazurenko.shootapp

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.android.camera2basic.IRepo
import kotlinx.android.synthetic.main.fragment_filelist.*

class GalleryFragment : Fragment(), IGalleryFragmentView {
    override fun displayPictures(pics: List<Bitmap>) {
        with (rvList.adapter as FileListAdapter) {
            datasource.addAll(pics)
            notifyDataSetChanged()
        }
    }

    var presenter: GalleryPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = GalleryPresenter(this, IRepo.getInstance(inflater.context))
        return inflater.inflate(R.layout.fragment_filelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.adapter = FileListAdapter()
        rvList.layoutManager = LinearLayoutManager(context)
        with(view as SwipeRefreshLayout) {
            setOnRefreshListener{presenter?.getData()}
            isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.getData()
    }


    class FileListAdapter : RecyclerView.Adapter<FileListAdapter.MyHolder>() {
        val datasource = ArrayList<Bitmap>()

        class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

        override fun getItemCount(): Int  = datasource.size

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            with(holder.itemView as ImageView) {
                setImageBitmap(datasource[position])
            }
        }
    }
}