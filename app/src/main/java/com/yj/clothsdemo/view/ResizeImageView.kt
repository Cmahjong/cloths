package com.yj.clothsdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



/**
 * desc:
 * time: 2019/12/7
 * @author yinYin
 */
class ResizeImageView : ImageView {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {


        if (drawable != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            //高度根据使得图片的宽度充满屏幕计算而得
            val height = Math.ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
            setMeasuredDimension(width, height)        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }
}