package com.bshpanchuk.testyalantis.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .transition(withCrossFade())
        .into(this)
}
