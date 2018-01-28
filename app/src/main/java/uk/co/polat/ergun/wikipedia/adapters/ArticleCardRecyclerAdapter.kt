package uk.co.polat.ergun.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.holders.CardViewHolder

/**
 * Created by Ergun Polat on 28/01/2018.
 */

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardViewHolder>() {

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item, parent, false)
        return CardViewHolder(cardItem)
    }

}