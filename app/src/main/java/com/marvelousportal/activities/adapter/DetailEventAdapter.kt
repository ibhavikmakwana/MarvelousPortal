package com.marvelousportal.activities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.marvelousportal.R
import com.marvelousportal.activities.DetailActivity
import com.marvelousportal.models.Result
import com.marvelousportal.utils.Constant
import kotlinx.android.synthetic.main.layout_list_items.view.*
import java.util.*
import java.util.regex.Pattern


/**
 * Created by Bhavik Makwana on 1/30/2018.
 */

class DetailEventAdapter(context: Context, comics: List<Result>) : RecyclerView.Adapter<DetailEventAdapter.ItemViewHolder>() {

    private var mResult: List<Result>? = ArrayList()
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
        holder.charName.text = resultInfo?.title
        val path = resultInfo?.thumbnail?.path + "." + resultInfo?.thumbnail?.extension
        Log.i("IMAGE_PATH", path)
        Glide.with(mContext).load(path).into(holder.charImage)
        holder.rootLayout.setOnClickListener {
            DetailActivity.launchActivity(mContext!!, resultInfo?.id!!, Constant.EVENTS)
        }
    }

    override fun getItemCount(): Int {
        return mResult!!.size
    }

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var charName = itemView.tv_list_name_title!!
        var charImage = itemView.iv_list_name_image!!
        var rootLayout = itemView.row_root_list!!

    }

    fun setUserList(character: List<Result>?) {
        mResult = character
        notifyDataSetChanged()
    }

    private fun lastWord(sentence: String): String {
        val p = Pattern.compile("([\\p{Alpha}]+)(?=\\p{Punct}*$)")
        val m = p.matcher(sentence)
        return if (m.find()) {
            m.group()
        } else ""
    }
}
