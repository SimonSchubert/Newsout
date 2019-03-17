package com.inspiredandroid.newsout.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/*
 * Copyright 2019 Simon Schubert Use of this source code is governed by the Apache 2.0 license
 * that can be found in the LICENSE file.
 *
 * Stores the views width and height in a map indexed by a unique view id. The next time a view with the same id will be
 * loaded it restores the width and height.
 */
class AspectRatioHolderImageView : AppCompatImageView {

    internal var id: String = ""

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (measuredWidth != 0 && measuredHeight != 0) {
            WIDTHS[id] = measuredWidth
            HEIGHTS[id] = measuredHeight
        } else if (WIDTHS.containsKey(id)) {
            setMeasuredDimension(WIDTHS[id] ?: 0, HEIGHTS[id] ?: 0)
        }
    }

    /**
     * Pass an unique identifier for each view
     */
    fun init(id: String) {
        this.id = id
        if (WIDTHS.containsKey(id)) {
            setMeasuredDimension(WIDTHS[id] ?: 0, HEIGHTS[id] ?: 0)
        }
    }

    companion object {
        var WIDTHS = HashMap<String, Int>()
        var HEIGHTS = HashMap<String, Int>()
    }
}