package com.marvelousportal.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.marvelousportal.R
import com.marvelousportal.activities.adapter.CharactersEventAdapter
import com.marvelousportal.activities.adapter.CoreComicsAdapter
import com.marvelousportal.activities.adapter.TilesComicsAdapter
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseActivity
import com.marvelousportal.models.Item
import com.marvelousportal.models.Result
import com.marvelousportal.utils.ActivityUtils
import com.marvelousportal.utils.Constant
import com.marvelousportal.utils.Constant.Companion.CHARACTERS
import com.marvelousportal.utils.Constant.Companion.COMICS
import com.marvelousportal.utils.Constant.Companion.EVENTS
import com.marvelousportal.utils.Constant.Companion.SERIES
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailActivity : BaseActivity() {

    var bitmapImage: Bitmap? = null
    private var mTileIssueAdapter: TilesComicsAdapter? = null
    private var tileIssueList: MutableList<Item>? = null

    private var mCoreComicsAdapter: CoreComicsAdapter? = null
    private var coreIssueList: MutableList<Item>? = null

    private var mCharactersEventAdapter: CharactersEventAdapter? = null
    private var characterEventList: MutableList<Item>? = null

    companion object {
        const val ID = "ID"
        val DETAILTYPE = "DETAILTYPE"

        /**
         * call this method to launch the Main Activity
         */

        fun launchActivity(context: Context, id: Int, type: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(this.ID, id)
            intent.putExtra(DETAILTYPE, type)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    /**
     * initialize the app
     */
    private fun init() {
        val id = intent.getIntExtra(ID, 0)
        if (id > 0) {
            checkForType(id)
        }

        tileIssueList = ArrayList()
        coreIssueList = ArrayList()
        characterEventList = ArrayList()
        mTileIssueAdapter = TilesComicsAdapter(this, tileIssueList!!)
        mCoreComicsAdapter = CoreComicsAdapter(this, coreIssueList!!)
        mCharactersEventAdapter = CharactersEventAdapter(this, characterEventList!!)

        //nested scroll view listener to change the color of toolbar and status bar according to image and scroll
        nested_details.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                //scroll down
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val actionBar = supportActionBar
                    val colorDrawable = ColorDrawable(getDominantColor(this.bitmapImage!!))
                    actionBar!!.setBackgroundDrawable(colorDrawable)
                    window.statusBarColor = colorDrawable.color
                }
            }
            if (scrollY < oldScrollY) {
                //scroll up
            }

            if (scrollY == 0) {
                //scroll top
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityUtils.setActionBarColor(this, R.color.colorPrimary)
                    window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
                }
            }

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                //scroll bottom
            }
        })

        //image click listener for zoom
        iv_detail_image.setOnClickListener {

            val stream = ByteArrayOutputStream()
            bitmapImage?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            if (bytes != null) {
                ZoomImageActivity.launchActivity(this, bytes)
            }
        }
    }

    private fun checkForType(id: Int) {
        val type = intent.getStringExtra(DETAILTYPE)
        when (type) {
            COMICS -> comicDetails(id)
            CHARACTERS -> characterDetails(id)
            EVENTS -> eventsDetails(id)
            SERIES -> seriesDetails(id)
        }
    }

    private fun characterDetails(id: Int) {
        val appController = AppController.create(this)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchCharacterDetail(id,
                timeStamp,
                Constant.PUBLIC_KEY,
                getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            setUpDetail(userResponse.data.results)
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    private fun comicDetails(id: Int) {
        val appController = AppController.create(this)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchComicsDetail(id,
                timeStamp,
                Constant.PUBLIC_KEY,
                getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            setUpDetail(userResponse.data.results)
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    /**
     * set up the details of the character
     *//*
    private fun setUpCharacterDetail(results: List<Result>) {
        for (details in results) {
            //manage visibility
            ll_detail_published.visibility = View.GONE
            ll_detail_role.visibility = View.GONE

            val imageUrl = details.thumbnail.path + "." + details.thumbnail.extension
            *//*Glide.with(this).load(imageUrl).into(iv_detail_image)*//*
            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                            iv_detail_image.setImageBitmap(resource)
                            bitmapImage = resource
                        }
                    })
            tv_details_title.text = details.name
            if (details.description != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_detail_description.text = Html.fromHtml(details.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    tv_detail_description.text = Html.fromHtml(details.description)
                }
            }
        }
    }

    */
    /**
     * set up the details of the character
     *//*
    @SuppressLint("SetTextI18n")
    private fun setUpComicDetail(results: List<Result>) {
        for (details in results) {
            //manage visibility
            ll_detail_published.visibility = View.VISIBLE
            ll_detail_role.visibility = View.VISIBLE

            val imageUrl = details.thumbnail.path + "." + details.thumbnail.extension
            *//*Glide.with(this).load(imageUrl).into(iv_detail_image)*//*

            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                            iv_detail_image.setImageBitmap(resource)
                            bitmapImage = resource
                        }
                    })

            tv_details_title.text = details.title
            tv_detail_published.text = getFormattedDate(details.dates[0].date)

            if (details.description != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_detail_description.text = Html.fromHtml(details.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    tv_detail_description.text = Html.fromHtml(details.description)
                }
            }

            //set roles
            for (roles in details.creators.items) {
                // creating TextView programmatically
                val tvDynamic = BaseTextView(this)
                tvDynamic.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                tvDynamic.setPadding(0, 4, 0, 4)
                tvDynamic.text = roles.role + ":" + roles.name
                // add TextView to LinearLayout
                ll_detail_role.addView(tvDynamic)
            }
        }
    }*/

    /**
     * get the formatted date
     */
    @SuppressLint("SimpleDateFormat")
    private fun getFormattedDate(date: String): CharSequence? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeFormat = SimpleDateFormat("dd MMM, yyy")
        return timeFormat.format(myDate)
    }

    private fun seriesDetails(id: Int) {
        val appController = AppController.create(this)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchSeriesDetail(id,
                timeStamp,
                Constant.PUBLIC_KEY,
                getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            setUpDetail(userResponse.data.results)
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    private fun eventsDetails(id: Int) {
        val appController = AppController.create(this)
        val usersService = appController.apiService
        val timeStamp = getTimestamp()
        val disposable = usersService?.fetchEventsDetail(id,
                timeStamp,
                Constant.PUBLIC_KEY,
                getHash(timeStamp))?.subscribeOn(appController.subscribeScheduler())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ userResponse ->
            setUpDetail(userResponse.data.results)
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    /**
     * set up the details of the character
     */
    @SuppressLint("SetTextI18n")
    private fun setUpDetail(results: List<Result>) {
        for (details in results) {
            val imageUrl = details.thumbnail.path + "." + details.thumbnail.extension
            /*Glide.with(this).load(imageUrl).into(iv_detail_image)*/
            Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                            iv_detail_image.setImageBitmap(resource)
                            bitmapImage = resource
                        }
                    })

            if (intent.getStringExtra(DETAILTYPE) == EVENTS) {
                ll_detail_published.visibility = View.VISIBLE
                tv_start_date.text = getFormattedDate(details.start)
                tv_end_date.text = getFormattedDate(details.end)
            }

            //set name or title
            if (details.name != null) {
                tv_details_title.text = details.name
                tv_detail_tile_issues.text = details.name + getString(R.string.in_issue)
                tv_detail_core_issues.text = details.name + getString(R.string.core_issues)
                title = details.name
            } else {
                tv_details_title.text = details.title
                tv_detail_tile_issues.text = details.title + getString(R.string.in_issue)
                tv_detail_core_issues.text = details.title + getString(R.string.core_issues)
                title = details.title
            }

            //set published date
            /*if (details.dates[0].date.isNullOrEmpty()) {
                tv_detail_published.text = getFormattedDate(details.dates[0].date)
            }*/

            if (!details.description.isNullOrEmpty()) {
                tv_detail_description.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_detail_description.text = Html.fromHtml(details.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    tv_detail_description.text = Html.fromHtml(details.description)
                }
            } else {
                tv_detail_description.visibility = View.GONE
            }

            //set roles
            /*if (details.creators != null) {
                //manage visibility
                ll_detail_role.visibility = View.VISIBLE
                for (roles in details.creators.items) {
                    // creating TextView programmatically
                    val tvDynamic = BaseTextView(this)
                    tvDynamic.setPadding(0, 4, 0, 4)
                    tvDynamic.text = roles.role + ":" + roles.name
                    // add TextView to LinearLayout
                    ll_detail_role.addView(tvDynamic)
                }
            }*/

            for (url in details.urls) {
                if (url.type == "detail") {
                    tv_detail.visibility = View.VISIBLE
                    tv_detail.setOnClickListener {
                        openWebUrls(url.url)
                    }
                }
                if (url.type == "wiki") {
                    tv_wiki.visibility = View.VISIBLE
                    tv_wiki.setOnClickListener {
                        openWebUrls(url.url)
                    }
                }
                if (url.type == "comiclink") {
                    tv_comic.visibility = View.VISIBLE
                    tv_comic.setOnClickListener {
                        openWebUrls(url.url)
                    }
                }
            }

            //set up the comics listing
            setUpTileInIssues(details.comics.items)
            setUpCoreIssue(details.series.items)
            setUpCharacterInEvents(details.characters.items)
            tileIssueList?.addAll(details.comics.items)
            coreIssueList?.addAll(details.series.items)

        }
    }

    private fun setUpCharacterInEvents(item: List<Item>) {
        if (item.isNotEmpty()) {
            rv_detail_characters_event.isNestedScrollingEnabled = false
            rv_detail_characters_event.visibility = View.VISIBLE
            tv_detail_characters_event.visibility = View.VISIBLE

            val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rv_detail_characters_event.layoutManager = layoutManager
            rv_detail_characters_event.adapter = mCharactersEventAdapter
            mCharactersEventAdapter?.setUserList(item)
        }
    }

    private fun setUpCoreIssue(item: List<Item>) {
        if (item.isNotEmpty()) {
            rv_detail_core_issues.isNestedScrollingEnabled = false
            rv_detail_core_issues.visibility = View.VISIBLE
            tv_detail_core_issues.visibility = View.VISIBLE

            val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rv_detail_core_issues.layoutManager = layoutManager
            rv_detail_core_issues.adapter = mCoreComicsAdapter
            mCoreComicsAdapter?.setUserList(item)
        }
    }

    private fun setUpTileInIssues(item: List<Item>) {
        if (item.isNotEmpty()) {
            rv_detail_tile_issues.isNestedScrollingEnabled = false
            rv_detail_tile_issues.visibility = View.VISIBLE
            tv_detail_tile_issues.visibility = View.VISIBLE

            val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rv_detail_tile_issues.layoutManager = layoutManager
            rv_detail_tile_issues.adapter = mTileIssueAdapter
            mTileIssueAdapter?.setUserList(item)
        }
    }

    /**
     * open the url in the web browser
     */
    private fun openWebUrls(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(url)
        startActivity(browserIntent)
    }


    /**
     * get Dominant color from the image view
     */
    private fun getDominantColor(bitmap: Bitmap): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
}