package pl.web

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class ContentDownloader {
    fun getHTML(url: String): Document {
        return Jsoup.connect(url).get()
    }
}
