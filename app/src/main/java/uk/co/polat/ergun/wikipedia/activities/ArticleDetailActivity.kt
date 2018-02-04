package uk.co.polat.ergun.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast
import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.WikiApplication
import uk.co.polat.ergun.wikipedia.managers.WikiManager
import uk.co.polat.ergun.wikipedia.models.WikiPage

class ArticleDetailActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null

    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)


        supportActionBar?.title = currentPage?.title

        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        article_detail_webview.loadUrl(currentPage!!.fullurl)

        wikiManager!!.addHistory(currentPage!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home) {
            finish()
        }
        else if (item.itemId == R.id.action_favorite) {
            try {
                if(wikiManager!!.getIsFavorite(currentPage!!.pageid!!)) {
                    wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                    FancyToast.makeText(this,"Article removed from favorites", FancyToast.LENGTH_LONG, FancyToast.INFO,false).show()
                } else {
                    wikiManager!!.addFavorites(currentPage!!)
                    FancyToast.makeText(this,"Article added to favorite", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show()
                }
            } catch (ex: Exception) {
                FancyToast.makeText(this,"Unable to update this article.", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show()
            }
        }

        return true
    }
}