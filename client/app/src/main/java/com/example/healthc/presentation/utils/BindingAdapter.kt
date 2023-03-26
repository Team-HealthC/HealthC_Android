package com.example.healthc.presentation.utils

import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.healthc.BuildConfig
import com.example.healthc.R
import timber.log.Timber

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
    fun loadIngredientImage(view: ImageView, src: String?, placeHolder: Drawable) {
        if (src != null) {
            Timber.d(src)
            Glide.with(view.context)
                .load(BuildConfig.SPOON_API_INGREDIENT_URL + src)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:imageProductUrl", "app:placeholder")
    fun loadProductImage(view: ImageView, src: String?, placeHolder: Drawable) {
        if (src != null) {
            Glide.with(view.context)
                .load(BuildConfig.SPOON_API_PRODUCT_URL + src + "-90x90.jpeg")
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("app:ByteArrayImage", "app:placeholder")
    fun loadByteArrayImage(view: ImageView, src: String?, placeHolder: Drawable) {
        if (src != null) {
            val image: ByteArray = Base64.decode(src, Base64.DEFAULT)
            Glide.with(view.context)
                .load(image)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions().fitCenter())
                .error(R.drawable.health_c)
                .into(view)
        }
    }
}