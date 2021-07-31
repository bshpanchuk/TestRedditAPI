package com.bshpanchuk.testyalantis.common

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface BaseResourceManager {

    fun getString(@StringRes resId: Int) : String

    fun getQuantityString(@PluralsRes resId: Int, text: Int) : String
}

class ResourceManager(private val context: Context) : BaseResourceManager {

    override fun getString(@StringRes resId: Int) = context.getString(resId)

    override fun getQuantityString(@PluralsRes resId: Int, text: Int): String {
        return context.resources.getQuantityString(resId, text, text)
    }
}