package uk.co.polat.ergun.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.holders.CardViewHolder
import uk.co.polat.ergun.wikipedia.models.WikiPage

/**
 * Created by Ergun Polat on 28/01/2018.
 */

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardViewHolder>() {


    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        var page = currentResults[position]
        holder?.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardViewHolder(cardItem)
    }

}