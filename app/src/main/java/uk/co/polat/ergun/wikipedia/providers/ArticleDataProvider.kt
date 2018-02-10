package uk.co.polat.ergun.wikipedia.providers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.polat.ergun.wikipedia.models.Urls
import uk.co.polat.ergun.wikipedia.models.WikiResult
import java.io.Reader

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class ArticleDataProvider {

    private val timeout = 5000 // 5000 milliseconds = 5 seconds.
    private val readTimeout = 60000 // 60000 milliseconds = 1 minute.

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Ergun Polat")
    }

    fun search(term: String, skip: Int, take: Int) : Single<WikiResult?>? {
        return Urls.getSearchUrl(term, skip, take)
                .httpGet()
                .timeout(timeout)
                .timeoutRead(readTimeout)
                .rx_object(WikipediaDataDeserializer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map{ if(it.component2()!=null) throw it.component2()!! else it.component1() as WikiResult }
    }


    fun getRandom(take: Int): Single<WikiResult?>? {
        return Urls.getRandomURl(take)
                .httpGet()
                .timeout(timeout)
                .timeoutRead(readTimeout)
                .rx_object(WikipediaDataDeserializer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map{ if(it.component2()!=null) throw it.component2()!! else it.component1() as WikiResult }


}

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }
}