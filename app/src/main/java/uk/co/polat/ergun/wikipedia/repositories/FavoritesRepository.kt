package uk.co.polat.ergun.wikipedia.repositories

import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import uk.co.polat.ergun.wikipedia.models.WikiPage
import uk.co.polat.ergun.wikipedia.models.WikiThumbnail

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class FavoritesRepository(val databaseOpenHelper: ArticleDatabaseOpenHelper) {
    private val TABLE_NAME: String = "Favorites"

    fun addFavorite(page: WikiPage) {
        databaseOpenHelper.use {
            insert(TABLE_NAME, "id" to page.pageid,
                    "title" to page.title,
                    "url" to page.fullurl,
                    "thumbnailJson" to Gson().toJson(page.thumbnail))
        }
    }

    fun removeFavoriteById(pageId: Int) {
        databaseOpenHelper.use {
            delete(TABLE_NAME, "id = {pageId}", "pageId" to pageId)
        }
    }

    fun isArticleFavorite(pageId: Int): Boolean {
        var pages = getAllFavorites()
        return pages.any { page ->
            page.pageid == pageId
        }
    }

    fun getAllFavorites() : ArrayList<WikiPage> {
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)
            pages.add(page)
        }

        return pages
    }

}