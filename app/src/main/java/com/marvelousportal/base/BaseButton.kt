package com.marvelousportal.base

import android.content.Context
import android.os.Build
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet

class BaseButton : AppCompatButton {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        //set type face
//        typeface = ResourcesCompat.getFont(context, R.font.san_francisco)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translationZ = 10f
        }
    }
}
