package com.marvelousportal.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marvelousportal.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

open class BaseFragment : Fragment() {
    /**
     * [CompositeDisposable] that holds all the subscriptions.
     */
    private val mCompositeDisposable = CompositeDisposable()
    protected lateinit var mContext: Context       //Instance of the caller

    /**
     * Add the subscription to the [CompositeDisposable].

     * @param disposable [Disposable]
     */
    protected fun addSubscription(disposable: Disposable?) {
        if (disposable == null) return
        mCompositeDisposable.add(disposable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dispose()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose()
    }

    protected fun dispose() = mCompositeDisposable.dispose()

    protected fun finish() = activity?.finish()

    private lateinit var mPermissionListener: PermissionListener

    private val REQ_CODE_PERMISSION: Int = 101

    /**
     * This method is used to request permission.
     *
     * @param permission         string array of permission.
     * @param permissionListener listener for permission result.
     */
    fun requestPermission(@NonNull permission: Array<String>, @NonNull permissionListener: PermissionListener) {
        mPermissionListener = permissionListener
        if (ContextCompat.checkSelfPermission(this.context!!,
                permission[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity!!,
                    permission,
                    REQ_CODE_PERMISSION)
        } else {
            mPermissionListener.onPermissionGranted()
        }
    }

    /**
     * It is the result of the permission on which user reacted.
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            REQ_CODE_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPermissionListener.onPermissionGranted()
                } else {
                    mPermissionListener.onPermissionDenied()
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
