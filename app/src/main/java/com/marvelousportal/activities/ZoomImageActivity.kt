package com.marvelousportal.activities

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.marvelousportal.R
import com.marvelousportal.base.BaseActivity
import kotlinx.android.synthetic.main.activity_zoom_image.*


class ZoomImageActivity : BaseActivity() {

    companion object {
        const val ZOOM_IMAGE = "IMAGE"
        /**
         * call this method to launch the Main Activity
         */
        fun launchActivity(context: Context, bitmapImage: ByteArray) {
            val intent = Intent(context, ZoomImageActivity::class.java)
            intent.putExtra(ZoomImageActivity.ZOOM_IMAGE, bitmapImage)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_image)
        val bytes = intent.getByteArrayExtra(ZOOM_IMAGE)
        val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        Log.i("IMAGE", bmp.toString())
        zoomImage.setImageBitmap(bmp)
    }
}
