package uk.co.polat.ergun.wikipedia.providers

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import uk.co.polat.ergun.wikipedia.models.Urls
import uk.co.polat.ergun.wikipedia.models.WikiResult
import java.io.Reader

/**
 * Created by Ergun Polat on 28/01/2018.
 */
class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Ergun Polat")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getSearchUrl(term, skip, take).httpGet()
                .responseObject(WikipediaDataDeserializer()) { _, response, result ->

                    if (response.statusCode != 200) {
                        throw Exception("Unable to get article")
                    }
                    val(data , _) = result
                    responseHandler.invoke(data as WikiResult)
                }
    }


    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getRandomURl(take).httpGet().responseObject(WikipediaDataDeserializer()) { _, response, result ->

            if (response.statusCode != 200) {
                throw Exception("Unable to get articles")
            }

            val(data, _) = result
            responseHandler.invoke(data as WikiResult)
        }
    }

    class WikipediaDataDeserializer: ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }
}