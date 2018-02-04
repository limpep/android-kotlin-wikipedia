package uk.co.polat.ergun.wikipedia.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.WikiApplication
import uk.co.polat.ergun.wikipedia.activities.SearchActivity
import uk.co.polat.ergun.wikipedia.adapters.ArticleCardRecyclerAdapter
import uk.co.polat.ergun.wikipedia.managers.WikiManager

import uk.co.polat.ergun.wikipedia.providers.ArticleDataProvider


/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    private var wikiManager: WikiManager? = null

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null

    var refresher: SwipeRefreshLayout? = null

    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = GridLayoutManager(activity,1)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }


        getRandomArticles()


        return view
    }

    private fun getRandomArticles() {
        refresher?.isRefreshing = true

        try {

            wikiManager?.getRandom(15, { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            })
        } catch (ex: Exception) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message).setTitle("oops!")
            val dialog = builder.create()
            dialog.show()
        }
    }

}// Required empty public constructor
