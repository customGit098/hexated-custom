package com.hexated

import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.apmap
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.fixUrl
import com.lagradost.cloudstream3.mainPageOf
import com.lagradost.cloudstream3.newTvSeriesSearchResponse
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor

class KissasianMx : Kissasian() {
    override var mainUrl = "https://kissasian.mx"
    override var name = "KissasianMx"
    override val contentInfoClass = "barContent"
    override val mainPage = mainPageOf(
		"Genre/Movie/LatestUpdate?page=" to "Movie Latest Update",
		"Genre/Drama/LatestUpdate?page=" to "Drama Latest Update",
		"Country/South-Korea/LatestUpdate?page=" to "South-Korea Latest Update",
		"Country/South-Korea/MostPopular?page=" to "South-Korea Most Popular",
		"Country/Thailand/LatestUpdate?page=" to "Thailand Latest Update",
		"Country/Thailand/MostPopular?page=" to "Thailand Most Popular",
		"Country/Taiwan/LatestUpdate?page=" to "Taiwan Latest Update",
		"Country/Taiwan/MostPopular?page=" to "Taiwan Most Popular",
		"Country/China/LatestUpdate?page=" to "China Latest Update",
		"Country/China/MostPopular?page=" to "China Most Popular",
        "Status/Ongoing?page=" to "Drama Ongoing",
        "Status/Completed?page=" to "Drama Completed",
        "Status/Completed?page=" to "Drama Completed",
        "Genre/Movie?page=" to "Movie",
        "Genre/School?page=" to "School",
        "Genre/Friendship?page=" to "Friendship",
        "Genre/Melodrama?page=" to "Melodrama",
        "Genre/Action?page=" to "Action",
        "Genre/Detective?page=" to "Detective",
        "Genre/Medical?page=" to "Medical",
        "Genre/Horror?page=" to "Horror",
        "Genre/Military?Military=" to "Military",
        "Genre/Historical?Historical=" to "Historical",
    )

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.post(
            "$mainUrl/Search/SearchSuggest", data = mapOf(
                "type" to "Drama",
                "keyword" to query,
            ), headers = mapOf("X-Requested-With" to "XMLHttpRequest")
        ).document
        return document.select("a").mapNotNull {
            val href = fixUrl(it.attr("href"))
            val title = it.text()
            newTvSeriesSearchResponse(title, href, TvType.AsianDrama)
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {

        val document = app.get(data).document
        document.select("select#selectServer option").apmap {
            val server = it.attr("value")
            val iframe = app.get(fixUrl(server ?: return@apmap)).document.selectFirst("div#centerDivVideo iframe")?.attr("src")
            loadExtractor(iframe ?: return@apmap, "$mainUrl/", subtitleCallback, callback)
        }

        return true
    }
}