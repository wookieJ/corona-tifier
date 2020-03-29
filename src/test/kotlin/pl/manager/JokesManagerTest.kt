package pl.manager

import org.apache.http.client.HttpResponseException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import pl.config.JokesProperties
import pl.web.ContentDownloader

internal class JokesManagerTest {
    private val jokesProperties: JokesProperties = JokesProperties(apiUrl = "http://jokes")
    private val contentDownloader: ContentDownloader = mock(ContentDownloader::class.java)
    private val jokesManager: JokesManager = JokesManager(jokesProperties, contentDownloader)

    @Test
    fun `Should return random joke`() {
        // given
        `when`(
            contentDownloader.getHTML("http://jokes/losuj")
        ).thenReturn(
            Jsoup.parse(
                """
                <html>
                    <head></head>
                    <body>
                        <div id="trescGlowna">
                            <div class="tekst">
                                <div class="tekst-pokaz">
                                    Very funny joke
                                </div>
                            </div>
                        </div>
                    </body>
                </html>
                """
            )
        )

        // when
        val joke: String = jokesManager.getRandomJoke()

        // then
        assertThat(joke).isEqualTo("Very funny joke")
    }

    @Test
    fun `Should return second joke from html jokes list if first is invalid`() {
        // given
        `when`(
            contentDownloader.getHTML("http://jokes/losuj")
        ).thenReturn(
            Jsoup.parse(
                """
                <html>
                    <head></head>
                    <body>
                        <div id="trescGlowna">
                            <div class="tekst">
                                <div class="tekst-pokaz">
                                </div>
                            </div>
                            <div class="tekst">
                                <div class="tekst-pokaz">
                                    Very funny joke
                                </div>
                            </div>
                        </div>
                    </body>
                </html>
                """
            )
        )

        // when
        val joke: String = jokesManager.getRandomJoke()

        // then
        assertThat(joke).isEqualTo("Very funny joke")
    }

    @Test
    fun `Should throw exception if html doesn't contain joke`() {
        // given
        `when`(
            contentDownloader.getHTML("http://jokes/losuj")
        ).thenReturn(
            Jsoup.parse(
                """
                <html>
                    <head></head>
                    <body></body>
                </html>
                """
            )
        )

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { jokesManager.getRandomJoke() }
            .withMessage("Problems with getting joke: ${JokesManager.JOKE_NOT_FOUND_MESSAGE}")
    }

    @Test
    fun `Should catch client exception when getting joke html content`() {
        // given
        `when`(
            contentDownloader.getHTML("http://jokes/losuj")
        ).thenThrow(HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server unavailable"))

        // then
        assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { jokesManager.getRandomJoke() }.withMessageStartingWith(
            "Problems with getting joke: 500 Server unavailable"
        )
    }

    @Test
    fun `Should throw exception when facebook api url not found`() {
        // given
        jokesProperties.apiUrl = null

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy {jokesManager.getRandomJoke() }
            .withMessageStartingWith("Problems with getting joke: Jokes api url property not found")
    }
}
