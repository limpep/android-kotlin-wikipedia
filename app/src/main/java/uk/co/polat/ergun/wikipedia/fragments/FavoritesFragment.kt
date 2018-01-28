package uk.co.polat.ergun.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.doAsync


import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.WikiApplication
import uk.co.polat.ergun.wikipedia.adapters.ArticleCardRecyclerAdapter
import uk.co.polat.ergun.wikipedia.managers.WikiManager
import uk.co.polat.ergun.wikipedia.models.WikiPage


/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment() {

    private var wikiManager: WikiManager? = null
    var favoritesRecycler: RecyclerView? = null

    private val adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity.applicationContext as WikiApplication).wikiManager
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater!!.inflate(R.layout.fragment_favorites, container, false)


        favoritesRecycler = view.findViewById<RecyclerView>(R.id.favorites_article_recycler)
        favoritesRecycler!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL )
        favoritesRecycler!!.adapter = adapter


        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager!!.getFavorites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)

            activity.runOnUiThread {
                adapter.notifyDataSetChanged()
            }

        }
    }

}// Required empty public constructor
