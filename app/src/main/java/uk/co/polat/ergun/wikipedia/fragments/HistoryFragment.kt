package uk.co.polat.ergun.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import org.jetbrains.anko.*


import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.WikiApplication

import uk.co.polat.ergun.wikipedia.adapters.ArticleListItemRecyclerAdapter
import uk.co.polat.ergun.wikipedia.managers.WikiManager
import uk.co.polat.ergun.wikipedia.models.WikiPage


/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    private var wikiManager: WikiManager? = null
    var historyRecycler: RecyclerView? = null

    private val adapter = ArticleListItemRecyclerAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater!!.inflate(R.layout.fragment_history, container, false)

        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)

        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val history = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(history as ArrayList<WikiPage>)

            activity.runOnUiThread {
                adapter.notifyDataSetChanged()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_clear_history) {
            activity.alert("Are you sure you want to clear history?", "Confirm") {
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager?.clearHistory()
                    }
                    activity.runOnUiThread { adapter.notifyDataSetChanged()}
                }
                noButton {

                }
            }.show()
        }
        return true
    }

}// Required empty public constructor
