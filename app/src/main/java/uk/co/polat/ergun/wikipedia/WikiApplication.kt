package uk.co.polat.ergun.wikipedia

import android.app.Application
import uk.co.polat.ergun.wikipedia.managers.WikiManager
import uk.co.polat.ergun.wikipedia.providers.ArticleDataProvider
import uk.co.polat.ergun.wikipedia.repositories.ArticleDatabaseOpenHelper
import uk.co.polat.ergun.wikipedia.repositories.FavoritesRepository
import uk.co.polat.ergun.wikipedia.repositories.HistoryRepository

/**
 * Created by Ergun Polat on 28/01/2018.
 */

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set



    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }

}