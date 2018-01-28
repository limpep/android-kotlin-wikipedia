package uk.co.polat.ergun.wikipedia.managers

import uk.co.polat.ergun.wikipedia.models.WikiPage
import uk.co.polat.ergun.wikipedia.models.WikiResult
import uk.co.polat.ergun.wikipedia.providers.ArticleDataProvider
import uk.co.polat.ergun.wikipedia.repositories.FavoritesRepository
import uk.co.polat.ergun.wikipedia.repositories.HistoryRepository

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class WikiManager(private val provider: ArticleDataProvider,
                  private val favoritesRepository: FavoritesRepository,
                  private val historyRepository: HistoryRepository) {

    private var favoriteCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search (term: String, skip: Int, take: Int, handler: (result: WikiResult) -> Unit?) {
        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?) {
        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null)
            historyCache = historyRepository.getAllHistory()
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage> ? {
        if (favoriteCache == null)
            favoriteCache = favoritesRepository.getAllFavorites()
        return favoriteCache
    }

    fun addFavorites(page: WikiPage) {
        favoriteCache?.add(page)
        favoritesRepository.addFavorite(page)
    }

    fun removeFavorite(pageId: Int) {
        favoritesRepository.removeFavoriteById(pageId)
        favoriteCache = favoriteCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int) : Boolean {
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addHistory(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory?.forEach { historyRepository.removeHistoryById(it.pageid!!) }
    }

}


