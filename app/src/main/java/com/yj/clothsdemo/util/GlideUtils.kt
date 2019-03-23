package com.yj.clothsdemo.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.yj.clothsdemo.R

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
object GlideUtils {
   fun loadPic(img: ImageView, url: String) {
      Glide.with(img).load(url).into(img)
   }
   fun loadPicHead(img: ImageView, url: String) {
      Glide.with(img).applyDefaultRequestOptions(RequestOptions().centerCrop().error(R.drawable.icon_head).placeholder(R.drawable.icon_head)).load(url).into(img)
   }
}