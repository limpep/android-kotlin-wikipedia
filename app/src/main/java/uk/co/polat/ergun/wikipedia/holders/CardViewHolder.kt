package uk.co.polat.ergun.wikipedia.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.article_card_item.view.*
import uk.co.polat.ergun.wikipedia.R

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.article_image)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.article_title)


}