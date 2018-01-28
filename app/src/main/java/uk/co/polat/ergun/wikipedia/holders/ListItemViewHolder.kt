package uk.co.polat.ergun.wikipedia.holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_card_item.view.*
import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.activities.ArticleDetailActivity
import uk.co.polat.ergun.wikipedia.models.WikiPage

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView: TextView = itemView.findViewById<TextView>(R.id.result_title)

    private var currentPage: WikiPage? = null


    init {
        itemView.setOnClickListener {
            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailPageIntent)

        }
    }

    fun updateWithPage(page: WikiPage) {
        currentPage = page
        titleTextView.text = page.title

        if (page.thumbnail != null) {
            Picasso.with(itemView.context).load(page.thumbnail!!.source).into(articleImageView)
        }
    }

}