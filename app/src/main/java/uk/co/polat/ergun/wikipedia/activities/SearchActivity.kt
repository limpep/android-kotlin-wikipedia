package uk.co.polat.ergun.wikipedia.activities


import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_search.*
import uk.co.polat.ergun.wikipedia.R

import android.app.SearchManager;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import com.pawegio.kandroid.onQueryChange
import com.pawegio.kandroid.onQuerySubmit
import uk.co.polat.ergun.wikipedia.WikiApplication
import uk.co.polat.ergun.wikipedia.adapters.ArticleListItemRecyclerAdapter
import uk.co.polat.ergun.wikipedia.managers.WikiManager
import uk.co.polat.ergun.wikipedia.providers.ArticleDataProvider


class SearchActivity : AppCompatActivity() {

    private var wikiManager: WikiManager? = null

    private var adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        search_results_recycler.layoutManager = LinearLayoutManager(this)
        search_results_recycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu!!.findItem(R.id.action_search)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()

        searchView.onQuerySubmit { query ->
            wikiManager?.search(query, 0, 20 , { wikiresult->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiresult.query!!.pages)
                runOnUiThread { adapter.notifyDataSetChanged() }
            })
        }

        searchView.onQueryChange { query ->
           if (query.length >= 3) {
               wikiManager?.search(query, 0, 20 , { wikiresult->
                   adapter.currentResults.clear()
                   adapter.currentResults.addAll(wikiresult.query!!.pages)
                   runOnUiThread { adapter.notifyDataSetChanged() }
               })
           }
        }

        return super.onCreateOptionsMenu(menu)
    }
}
