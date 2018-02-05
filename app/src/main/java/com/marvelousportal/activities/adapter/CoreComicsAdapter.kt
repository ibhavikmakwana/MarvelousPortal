package com.marvelousportal.activities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marvelousportal.R
import com.marvelousportal.models.Item
import kotlinx.android.synthetic.main.layout_list_items.view.*
import java.util.*

/**
 * Created by Bhavik Makwana on 1/30/2018.
 */

class CoreComicsAdapter(context: Context, comics: List<Item>) : RecyclerView.Adapter<CoreComicsAdapter.ItemViewHolder>() {

    private var mResult: List<Item>? = ArrayList()
    private var mContext: Context? = null

    init {
        mResult = comics
        mContext = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_items, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val resultInfo = mResult?.get(position)
        holder.charName.text = resultInfo?.name
    }

    override fun getItemCount(): Int {
        return mResult!!.size
    }

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var charName = itemView.tv_list_name_title!!
    }

    fun setUserList(character: List<Item>?) {
        mResult = character
        notifyDataSetChanged()
    }
}
