package com.marvelousportal.base

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import org.jetbrains.annotations.NotNull


class BaseTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(@Suppress("UNUSED_PARAMETER") context: Context) {
        //set type face
//        typeface = ResourcesCompat.getFont(context, R.font.san_francisco)
    }

    /**
     * @return Trimmed text.
     */
    fun getTrimmedText() = text.toString().trim { it <= ' ' }

    /**
     * This method is used to set the text and background tint based on the theme color of the user.
     */
    fun setTextAndBackTint(@NotNull color: Int) {
        setTextColor(ContextCompat.getColor(context, color))
        background.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY)
    }
}
