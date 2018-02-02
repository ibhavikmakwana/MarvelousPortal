package com.marvelousportal.base

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet

class BaseEditText : AppCompatEditText {
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
     * Check if the edit text is empty.

     * @return True uf there is no text entered in edit text.
     */
    fun isEmpty() = getTrimmedText().isEmpty()

    override fun setError(error: CharSequence?) {
        requestFocus()
        super.setError(error)
    }

    /**
     * @return Trimmed text.
     */
    fun getTrimmedText() = text.toString().trim { it <= ' ' }

    fun clear() = setText("")
}
