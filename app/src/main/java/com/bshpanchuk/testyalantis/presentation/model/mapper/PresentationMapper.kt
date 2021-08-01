package com.bshpanchuk.testyalantis.presentation.model.mapper

import com.bshpanchuk.testyalantis.R
import com.bshpanchuk.testyalantis.common.BaseResourceManager
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import com.bshpanchuk.testyalantis.presentation.model.RedditPostUI
import java.util.*
import java.util.concurrent.TimeUnit

class PresentationMapper(private val resourceManager: BaseResourceManager) :
    Mapper<ItemRedditPost, RedditPostUI> {

    override fun map(oldData: ItemRedditPost): RedditPostUI {
        return with(oldData) {
            RedditPostUI(
                name = name,
                title = title,
                author = "•$author",
                subreddit = "r/$subreddit",
                data = formatData(data),
                imageUrl = imageUrl,
                rating = formatNumbers(rating),
                numberOfComments = formatNumbers(numberOfComments),
                link = link
            )
        }
    }

    private fun formatNumbers(quantity: Int): String {
        if (quantity < 1000) return quantity.toString()
        return String.format(Locale.ENGLISH, "%.1f", quantity / 1000.0) + "k"
    }

    private fun formatData(timeCreated: Long): String {
        val timeDiff = System.currentTimeMillis() - (timeCreated * 1000)

        val years = (timeDiff / (1000L * 60 * 60 * 24 * 365) % 365)
        if (years > 0) return resourceManager.getQuantityString(R.plurals.years_ago, years.toInt())

        val month = (timeDiff / (1000L * 60 * 60 * 24 * 30) % 30)
        if (month > 0) return resourceManager.getQuantityString(R.plurals.months_ago, month.toInt())

        val days = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS)
        if (days > 0) return resourceManager.getQuantityString(R.plurals.days_ago, days.toInt())

        val hours = TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS)
        if (hours > 0) return resourceManager.getQuantityString(R.plurals.hours_ago, hours.toInt())

        val minutes = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS)
        return resourceManager.getQuantityString(R.plurals.minutes_ago, minutes.toInt())
    }

}
