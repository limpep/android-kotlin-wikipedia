package uk.co.polat.ergun.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.holders.CardViewHolder
import uk.co.polat.ergun.wikipedia.holders.ListItemViewHolder

/**
 * Created by Ergun Polat on 28/01/2018.
 */

class ArticleListItemRecyclerAdapter() : RecyclerView.Adapter<ListItemViewHolder>() {

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ListItemViewHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemViewHolder {
        var listItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemViewHolder(listItem)
    }

}