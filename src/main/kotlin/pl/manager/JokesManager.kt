package pl.manager

import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import pl.config.JokesProperties
import pl.logger
import pl.web.ContentDownloader

@Service
class JokesManager(private val jokesProperties: JokesProperties, private val contentDownloader: ContentDownloader) {
    fun getRandomJoke(): String {
        try {
            jokesProperties.apiUrl ?: throw Exception("Jokes api url property not found")
            contentDownloader.getHTML("${jokesProperties.apiUrl}/losuj").run {
                select("#trescGlowna .tekst .tekst-pokaz").forEach { element ->
                    val jokeBody = element.text()
                    if (jokeBody.isNotEmpty()) {
                        return jokeBody.removeSuffix(" | PiszSuchary.pl")
                    }
                }
                throw Exception(JOKE_NOT_FOUND_MESSAGE)
            }
        } catch (httpException: HttpClientErrorException) {
            logger.error("Problems with getting joke: ${httpException.message}")
            throw HttpResponseException(httpException.statusCode.value(), "Problems with getting joke: ${httpException.message}")
        } catch (exception: Exception) {
            logger.error("Problems with getting joke: ${exception.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting joke: ${exception.message}")
        }
    }

    companion object {
        private val logger by logger()
        const val JOKE_NOT_FOUND_MESSAGE = "Nie znalazłem żadnego żartu :( spróbuj ponownie :)"
    }
}
