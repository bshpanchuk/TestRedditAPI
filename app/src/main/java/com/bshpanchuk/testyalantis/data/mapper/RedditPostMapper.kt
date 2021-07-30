package com.bshpanchuk.testyalantis.data.mapper

import com.bshpanchuk.testyalantis.data.model.DataChildren
import com.bshpanchuk.testyalantis.data.model.ResultRedditDTO
import com.bshpanchuk.testyalantis.domain.model.DataPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper
import java.util.*

class RedditPostMapper : Mapper<ResultRedditDTO, DataPost> {

    override fun map(oldData: ResultRedditDTO): DataPost {
        return with(oldData) {
            DataPost(
                after = data.after,
                before = data.before,
                itemsPost = data.children.map { mapItemPodcast(it.data) }
            )
        }
    }

    private fun mapItemPodcast(oldData: DataChildren) : DataPost.PostItem {
        return with(oldData){
            DataPost.PostItem(
                title = title,
                author =  "•$author",
                subreddit = subredditNamePrefixed,
                data = calcData(createdUtc),
                imageUrl = url,
                rating = formatNumbers(score),
                numberOfComments = formatNumbers(numComments),
                link = permalink
            )
        }
    }

    private fun formatNumbers(quantity: Int) : String{
        if (quantity < 1000) return quantity.toString()
        return String.format(Locale.ENGLISH ,"%.1f" , quantity / 1000.0) + "k"
    }

    private fun calcData(createdTime: Double): String {
        val timeMillisPost = createdTime.toLong() * 1000
        val timeMillisNow = System.currentTimeMillis()

        val timePost = GregorianCalendar().apply {
            timeInMillis = timeMillisPost
            timeZone = TimeZone.getTimeZone("UTC")
        }

        val timeNow = GregorianCalendar().apply {
            timeInMillis = timeMillisNow
            timeZone = TimeZone.getDefault()
        }

        (timeNow.get(Calendar.YEAR) - timePost.get(Calendar.YEAR)).let { years ->
            if (years > 0) return "$years years ago"
        }

        (timeNow.get(Calendar.MONTH) - timePost.get(Calendar.MONTH)).let { mounts ->
            if (mounts > 0) return "$mounts mounts ago"
        }

        (timeNow.get(Calendar.DAY_OF_YEAR) - timePost.get(Calendar.DAY_OF_YEAR)).let { days ->
            if (days > 0) return "$days days ago"
        }

        (timeNow.get(Calendar.HOUR) - timePost.get(Calendar.HOUR)).let { hours ->
            if (hours > 0) return "$hours hours ago"
        }

        return "${timeNow.get(Calendar.MINUTE) - timePost.get(Calendar.MINUTE)} minutes ago"
    }
}