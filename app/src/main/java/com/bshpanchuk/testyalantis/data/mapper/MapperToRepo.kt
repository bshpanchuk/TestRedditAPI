package com.bshpanchuk.testyalantis.data.mapper

import com.bshpanchuk.testyalantis.data.db.model.DataReddit
import com.bshpanchuk.testyalantis.data.db.model.ItemRedditDb
import com.bshpanchuk.testyalantis.data.model.DataChildren
import com.bshpanchuk.testyalantis.data.model.ResultReddit
import com.bshpanchuk.testyalantis.domain.model.mapper.Mapper

class MapperToRepo : Mapper<ResultReddit, DataReddit> {

    override fun map(oldData: ResultReddit): DataReddit {
        return with(oldData) {
            DataReddit(
                after = data.after,
                before = data.before,
                itemsPost = data.children.map { mapItemPodcast(it.data) }
            )
        }
    }

    private fun mapItemPodcast(oldData: DataChildren) : ItemRedditDb {
        return with(oldData){
            ItemRedditDb(
                name = name,
                title = title,
                author =  author,
                subreddit = subreddit,
                data = created,
                imageUrl = thumbnail,
                rating = score,
                numberOfComments = numComments,
                link = permalink
            )
        }
    }
}