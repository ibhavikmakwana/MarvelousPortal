/*
 *  Copyright 2017 Keval Patel.
 *
 *  Licensed under the GNU General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.marvelousportal.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.marvelousportal.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private val REQ_CODE_PERMISSION = 1
    private var mPermissionListener: PermissionListener? = null

    val disposables = CompositeDisposable()
    protected var isNetworkCallRunning = false

    protected fun networkingCallRunning() {
        isNetworkCallRunning = true
    }

    protected fun networkingCallComplete() {
        isNetworkCallRunning = false
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    /**
     * Set the toolbar of the activity.

     * @param toolbarId    resource id of the toolbar
     * *
     * @param title        title of the activity
     * *
     * @param showUpButton true if toolbar should display up indicator.
     */
    fun setToolbar(toolbarId: Int,
                   @StringRes title: Int,
                   showUpButton: Boolean) {
        val toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        setToolbar(title, showUpButton)
    }

    /**
     * Set the toolbar of the activity.

     * @param toolbarId    resource id of the toolbar
     * *
     * @param title        title of the activity
     * *
     * @param showUpButton true if toolbar should display up indicator.
     */
    fun setToolbar(toolbarId: Int,
                   title: String?,
                   showUpButton: Boolean) {
        val toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        setToolbar(title, showUpButton)
    }

    /**
     * Set the toolbar.

     * @param title        Activity title string resource
     * *
     * @param showUpButton true if toolbar should display up indicator.
     */
    protected fun setToolbar(@StringRes title: Int,
                             showUpButton: Boolean) {
        setToolbar(getString(title), showUpButton)
    }

    /**
     * Set the toolbar.

     * @param title        Activity title string.
     * *
     * @param showUpButton true if toolbar should display up indicator.
     */
    @SuppressLint("RestrictedApi")
    protected fun setToolbar(title: String?,
                             showUpButton: Boolean) {
        //set the title
        supportActionBar!!.title = title ?: ""

        //Set the up indicator
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(showUpButton)
        supportActionBar!!.setHomeButtonEnabled(showUpButton)
        supportActionBar!!.setDisplayHomeAsUpEnabled(showUpButton)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            //Hide the keyboard if any view is currently in focus.
//            if (currentFocus != null) ViewUtils.hideKeyboard(currentFocus)
            supportFinishAfterTransition()
            false
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Add the subscription to the [CompositeDisposable].

     * @param disposable [Disposable]
     */
    fun addSubscription(disposable: Disposable?) {
        if (disposable == null) return
        disposables.add(disposable)
    }

    public override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    fun showSnackbar(@StringRes message: Int,
                     @StringRes actionName: Int,
                     onActionClick: View.OnClickListener?) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_SHORT)
                .setAction(actionName, onActionClick)
                .show()
    }

    fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * This method is used to request permission.
     *
     * @param permission         string array of permission.
     * @param permissionListener listener for permission result.
     */
    fun requestPermission(@NonNull permission: Array<String>, @NonNull permissionListener: PermissionListener) {
        mPermissionListener = permissionListener
        if (ContextCompat.checkSelfPermission(this,
                permission[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permission,
                    REQ_CODE_PERMISSION)
        } else {
            mPermissionListener!!.onPermissionGranted()
        }
    }

    /**
     * It is the result of the permission on which user reacted.
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            REQ_CODE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPermissionListener!!.onPermissionGranted()
                } else {
                    mPermissionListener!!.onPermissionDenied()
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date())
    }

    fun getHash(timeStamp: String): String {
        try {
            val hashString = timeStamp + Constant.PRIVATE_KEY + Constant.PUBLIC_KEY
            val md = MessageDigest.getInstance("MD5")
            md.update(hashString.toByteArray())
            val byteData = md.digest()
            //convert the byte to hex format method 1
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(Integer.toString((byteData[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }

            //convert the byte to hex format method 2
            val hexString = StringBuffer()
            for (i in byteData.indices) {
                val hex = Integer.toHexString(0xff and byteData[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }

            return hexString.toString()

        } catch (e: Exception) {
            // TODO: handle exception
        }

        return ""
    }
}
