package uk.co.polat.ergun.wikipedia.models

/**
 * Created by Ergun Polat on 28/01/2018.
 */
object Urls {
//    val BaseUrl = "https://en.wikipedia.org/w/api.php"
    val BaseUrl = "http://localhost"

    fun getSearchUrl(term: String, skip: Int, take: Int): String {
        return BaseUrl + "?action=query" +
                "&formatversion=2" +
                "&generator=prefixsearch" +
                "&gpssearch=$term" +
                "&gpslimit=$take" +
                "&gpsoffset=$skip" +
                "&prop=pageimages|info" +
                "&piprop=thumbnail|url" +
                "&pithumbsize=200" +
                "&pilimit=$take" +
                "&wbptterms=description" +
                "&format=json" +
                "&inprop=url"
    }

    fun getRandomURl(take: Int) : String {
        return BaseUrl + "?action=query" +
                "&format=json" +
                "&formatversion=2" +
                "&generator=random" +
                "&grnnamespace=0" +
                "&prop=pageimages|info" +
                "&grnlimit=$take" +
                "&inprop=url" +
                "&pithumbsize=200"
    }
}