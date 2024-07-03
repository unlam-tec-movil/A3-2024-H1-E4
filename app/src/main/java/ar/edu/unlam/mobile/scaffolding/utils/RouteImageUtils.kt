package ar.edu.unlam.mobile.scaffolding.utils

object RouteImageUtils {
    fun getTourDoneUrl(routeString: String): String =
        "https://api.mapbox.com/styles/v1/mapbox/streets-v12/static/path-5+4dc6d1-1($routeString)/auto/350x250?access_token=pk.eyJ1IjoiZnNrdXJuaWsiLCJhIjoiY2x2MTRneXBtMDB6eDJpcGxlbG9iZzM3ciJ9.diQaafPkgGDUGHs8R_m9kQ"
}
