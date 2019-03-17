package com.inspiredandroid.newsout.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/* Copyright 2019 Simon Schubert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
class AspectRatioHolderImageView : ImageView {

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