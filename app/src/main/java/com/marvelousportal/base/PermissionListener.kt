package com.marvelousportal.base

/**
 * This is the permission listener which is used to communicate with the activity to show the response.
 */
interface PermissionListener {
    //it is called when permission granted.
    fun onPermissionGranted()

    //it is called when user denies for the permission.
    fun onPermissionDenied()
}