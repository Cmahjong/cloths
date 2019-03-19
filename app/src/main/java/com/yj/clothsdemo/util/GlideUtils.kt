package com.yj.clothsdemo.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.BaseRequestOptions

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
object GlideUtils {
   fun loadPic(img: ImageView, url: String) {
      Glide.with(img).load(url).into(img)
   }
}