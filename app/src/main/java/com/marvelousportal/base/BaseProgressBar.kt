package com.marvelousportal.base

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar

class BaseProgressBar : ProgressBar {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}
