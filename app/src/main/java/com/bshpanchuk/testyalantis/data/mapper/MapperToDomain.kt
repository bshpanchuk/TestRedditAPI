package com.bshpanchuk.testyalantis.data.mapper

import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.domain.model.ItemRedditPost
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper

class MapperToDomain : Mapper<ItemRedditDb, ItemRedditPost> {
    override fun map(oldData: ItemRedditDb): ItemRedditPost {
        return with(oldData){
            ItemRedditPost(
                name = name,
                title = title,
                author = author,
                subreddit = subreddit,
                data = data,
                imageUrl = imageUrl,
                rating = rating,
                numberOfComments = numberOfComments,
                link = link
            )
        }
    }
}