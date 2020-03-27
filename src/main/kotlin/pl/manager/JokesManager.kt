package pl.manager

import org.apache.http.client.HttpResponseException
import org.jsoup.Jsoup
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import pl.config.JokesProperties
import pl.logger

@Component
class JokesManager(val jokesProperties: JokesProperties) {
    fun getRandomJoke(): String {
        try {
            Jsoup.connect("${jokesProperties.apiUrl}/losuj").get().run {
                select("#trescGlowna .tekst .tekst-pokaz").forEach { element ->
                    val jokeBody = element.text()
                    if (jokeBody.isNotEmpty()) {
                        return jokeBody
                    }
                }
                return JOKE_NOT_FOUND_MESSAGE
            }
        } catch (httpException: HttpClientErrorException) {
            logger.error("Problems with getting joke: ${httpException.message}")
            throw HttpResponseException(httpException.statusCode.value(), "Problems with getting joke: ${httpException.message}")
        } catch (exception: Exception) {
            logger.error("Problems with getting country joke: ${exception.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting country joke: ${exception.message}")
        }
    }

    companion object {
        private val logger by logger()
        private const val JOKE_NOT_FOUND_MESSAGE = "Nie znalazłem żadnego żartu :( spróbuj ponownie :)"
    }
}
