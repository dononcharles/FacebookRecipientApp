package com.chaldrac.facebookrecipientapp.lib.base

import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, URL: String)
    fun setOnFinishedImageLoadinListener(listener: Any)
}
