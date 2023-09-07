package com.healthc.app.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.healthc.app.R
import com.healthc.data.BuildConfig

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
    @BindingAdapter("app:imageIngredientUrl", "app:placeholder")
    fun loadIngredientImage(view: ImageView, src: Int, placeHolder: Drawable) {
        Glide.with(view.context)
            .load(src)
            .placeholder(placeHolder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(RequestOptions().fitCenter())
            .error(R.drawable.health_c)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("app:imageProductUrl", "app:placeholder", "app:imageType")
    fun loadProductImage(view: ImageView, src: Int, placeHolder: Drawable, imageType: String) {
        if (src != 0) {
            Glide.with(view.context)
                .load(BuildConfig.SPOON_API_PRODUCT_URL + src + "-312x231." + imageType)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:encodedImage", "app:placeholder")
    fun loadEncodedImage(view: ImageView, src: ByteArray, placeHolder: Drawable) {
        if (src.isNotEmpty()) {
            Glide.with(view.context)
                .load(src)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }
}