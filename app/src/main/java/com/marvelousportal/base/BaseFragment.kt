package com.marvelousportal.base

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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {
    /**
     * [CompositeDisposable] that holds all the subscriptions.
     */
    private val mCompositeDisposable = CompositeDisposable()
    protected lateinit var mContext: Context       //Instance of the caller

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dispose()
        return super.onCreateView(inflater, container, savedInstanceState)
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

    private fun dispose() = mCompositeDisposable.dispose()

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

    fun subscribe(disposable: Disposable): Disposable {
        mCompositeDisposable.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        mCompositeDisposable.clear()
    }
}
