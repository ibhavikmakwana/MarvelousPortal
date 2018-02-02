package com.marvelousportal.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.marvelousportal.R
import com.marvelousportal.app.AppController
import com.marvelousportal.base.BaseActivity
import com.marvelousportal.base.BaseTextView
import com.marvelousportal.models.Result
import com.marvelousportal.utils.ActivityUtils
import com.marvelousportal.utils.Constant
import com.marvelousportal.utils.Constant.Companion.CHARACTERS
import com.marvelousportal.utils.Constant.Companion.COMICS
import com.marvelousportal.utils.Constant.Companion.EVENTS
import com.marvelousportal.utils.Constant.Companion.SERIES
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : BaseActivity() {

    var bitmapImage: Bitmap? = null

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
        val id = intent.getIntExtra(ID, 0)
        if (id > 0) {
            checkForType(id)
        }
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
            setUpCharacterDetail(userResponse.data.results)
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
            setUpComicDetail(userResponse.data.results)
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
    }

    /**
     * set up the details of the character
     */
    private fun setUpCharacterDetail(results: List<Result>) {
        for (details in results) {
            //manage visibility
            ll_detail_published.visibility = View.GONE
            tv_detail_description.visibility = View.GONE
            ll_detail_role.visibility = View.GONE

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
            tv_details_title.text = details.name
        }
    }

    /**
     * set up the details of the character
     */
    @SuppressLint("SetTextI18n")
    private fun setUpComicDetail(results: List<Result>) {
        for (details in results) {
            //manage visibility
            ll_detail_published.visibility = View.VISIBLE
            ll_detail_role.visibility = View.VISIBLE

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
    }

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
            setUpCharacterDetail(userResponse.data.results)
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
            Log.i("Response",userResponse.data.results.toString())
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            Log.i("error", it.message)
        })
        addSubscription(disposable)
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