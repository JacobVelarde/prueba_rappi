package com.jacob.pruebarappi.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

class MyGridView: GridView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var heightSpec = heightMeasureSpec
        if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            heightSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 3, MeasureSpec.AT_MOST)
        }

        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}