package uk.co.polat.ergun.wikipedia.providers

import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.google.gson.Gson
import com.pawegio.kandroid.d
import com.pawegio.kandroid.e
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

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getSearchUrl(term, skip, take).httpGet()
                .timeout(timeout)
                .timeoutRead(readTimeout)
                .rx_object(WikipediaDataDeserializer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val statusCode = result.component2()?.response?.statusCode
                    when(statusCode) {
                        -1 -> e(statusCode.toString())
                        else -> {
                            val (data, _) = result
                            responseHandler.invoke(data as WikiResult)
                        }
                    }

                }, {
                    error -> e(error.cause.toString())
                })
    }


    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getRandomURl(take)
                .httpGet()
                .timeout(timeout)
                .timeoutRead(readTimeout)
                .rx_object(WikipediaDataDeserializer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val statusCode = result.component2()?.response?.statusCode

                    when(statusCode) {
                        -1 -> e(statusCode.toString(), result.component2()?.cause.toString())
                        else -> {
                            val (data, _) = result
                            responseHandler.invoke(data as WikiResult)
                        }
                    }

                }, {
                    error -> e(error.cause.toString())
                })
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }
}