package com.hexated

import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.mainPageOf

class Watchomovies : YomoviesProvider() {
    override var mainUrl = "https://watchomovies.mom"
    override var name = "Watchomovies"
    override val hasMainPage = true
    override var lang = "hi"
    override val supportedTypes = setOf(
        TvType.NSFW,
    )

    override val mainPage = mainPageOf(
        "most-favorites" to "Most Viewed",
        "genre/xxx-scenes" to "XXX Scenes",
        "genre/18" to "18+ Movies",
        "genre/erotic-movies" to "Erotic Movies Movies",
        "genre/parody" to "Parody Movies",
        "genre/tv-shows" to "TV Shows Movies",
    )
}