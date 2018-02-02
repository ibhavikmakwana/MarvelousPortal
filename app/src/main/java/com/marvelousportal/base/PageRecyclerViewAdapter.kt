package com.marvelousportal.base

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.marvelousportal.R
import com.marvelousportal.utils.inflate


abstract class PageRecyclerViewAdapter<VH : PageRecyclerViewAdapter.PageViewHolder>(collection: Collection<*>, listener: PageCompleteListener?)
    : RecyclerView.Adapter<PageRecyclerViewAdapter.PageViewHolder>() {

    companion object {
        @Suppress("PrivatePropertyName")
        val TYPE_LOADER = 5364
    }

    private val mCollection: Collection<*> = collection
    private val mListener: PageCompleteListener? = listener
    private var mHasNext = false

    @CallSuper
    override fun getItemCount(): Int = if (mHasNext && mListener != null) mCollection.size + 1 else mCollection.size

    @CallSuper
    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        //notify on pag complete
        if (mListener != null && mHasNext && position == mCollection.size - 1) mListener.onPageComplete()

        if (holder is LoaderViewHolder) {
            //Progressbar will start loading automatically.

        } else {
            //Call abstract method
            @Suppress("UNCHECKED_CAST")
            bindView(holder as VH, position)
        }
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return if (position == mCollection.size) TYPE_LOADER else prepareViewType(position)
    }

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PageViewHolder {
        return if (viewType == TYPE_LOADER) {
            LoaderViewHolder(parent!!.inflate(R.layout.layout_loader))
        } else {
            prepareViewHolder(parent, viewType)
        }
    }

    /**
     * Here you should bind the view holder with your view and data.

     * @param holder   [RecyclerView.ViewHolder]
     * *
     * @param position position of the row.
     */
    abstract fun bindView(holder: VH, position: Int)

    abstract fun prepareViewHolder(parent: ViewGroup?, viewType: Int): VH

    abstract fun prepareViewType(position: Int): Int


    fun setHasNext(hasNext: Boolean) {
        mHasNext = hasNext
    }

    /**
     * Loading view holder
     */
    internal class LoaderViewHolder(itemView: View?) : PageViewHolder(itemView)

    /**
     * Base view holder for pagination
     */
    open class PageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    /**
     * Listener to get notify when the list ends.
     */
    interface PageCompleteListener {

        /**
         * Callback to call when whole list is displayed.
         */
        fun onPageComplete()
    }
}
