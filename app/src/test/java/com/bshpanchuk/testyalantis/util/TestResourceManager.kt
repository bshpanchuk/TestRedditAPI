package com.bshpanchuk.testyalantis.util

import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.common.BaseResourceManager
import java.lang.IllegalArgumentException

class TestResourceManager : BaseResourceManager {
    override fun getString(resId: Int): String {
        return ""
    }

    override fun getQuantityString(resId: Int, count: Int): String {
        return when (resId) {
            R.plurals.years_ago -> {
                if (count == 1) {
                    "$count year ago"
                } else {
                    "$count years ago"
                }
            }
            R.plurals.months_ago -> {
                if (count == 1) {
                    "$count month ago"
                } else {
                    "$count months ago"
                }
            }
            R.plurals.days_ago -> {
                if (count == 1) {
                    "$count day ago"
                } else {
                    "$count days ago"
                }
            }
            R.plurals.hours_ago -> {
                if (count == 1) {
                    "$count hour ago"
                } else {
                    "$count hours ago"
                }
            }
            else -> {
                if (count == 1) {
                    "$count minute ago"
                } else {
                    "$count minutes ago"
                }
            }
        }
    }
}