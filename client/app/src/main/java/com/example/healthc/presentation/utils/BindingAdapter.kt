package com.example.healthc.presentation.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.healthc.BuildConfig
import com.example.healthc.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl", "app:placeholder")
    fun loadImage(view: ImageView, src: String?, placeHolder: Drawable) {
        if (src != null) {
            Glide.with(view.context)
                .load(src)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageWebUrl", "app:placeholder")
    fun loadWebImage(view: ImageView, src: String?, placeHolder: Drawable) {
        if (src != null) {
            Glide.with(view.context)
                .load(BuildConfig.SPOON_API_BASE_URI + src)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }
}