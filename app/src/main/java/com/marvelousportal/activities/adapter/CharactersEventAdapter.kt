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
import java.util.regex.Pattern



/**
 * Created by Bhavik Makwana on 1/30/2018.
 */

class CharactersEventAdapter(context: Context, comics: List<Item>) : RecyclerView.Adapter<CharactersEventAdapter.ItemViewHolder>() {

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
        val id = resultInfo?.resourceURI
        holder.charName.setOnClickListener {
            /*val lastWord = id?.substring(id.lastIndexOf(" "))*/
            /*Log.i("ID", lastWord(id!!))*/
            /*DetailActivity.launchActivity(mContext!!, lastWord?.toInt()!!, Constant.CHARACTERS)*/
        }
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

    private fun lastWord(sentence: String): String {
        val p = Pattern.compile("([\\p{Alpha}]+)(?=\\p{Punct}*$)")
        val m = p.matcher(sentence)
        return if (m.find()) {
            m.group()
        } else ""
    }
}
