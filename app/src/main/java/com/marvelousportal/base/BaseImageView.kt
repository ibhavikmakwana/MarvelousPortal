package com.marvelousportal.base

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


class BaseImageView : AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}
