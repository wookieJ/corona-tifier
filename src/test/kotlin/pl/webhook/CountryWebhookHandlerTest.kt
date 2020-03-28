package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.manager.CasesManager
import org.mockito.Mockito.mock

internal class CountryWebhookHandlerTest {

    private val casesManager = mock(CasesManager::class.java)
    private val countryWebhookHandler = CountryWebhookHandler(casesManager)

    @Test
    fun `Should map country name`() {
        // given
        val countries: List<String> = listOf(
            "chiny", "chinach", "polska", "polsce", "włochy", "włoszech", "hiszpania", "niemcy", "francja", "japonia", "czechy", "usa"
        )

        // when
        val translations = countries
            .map { countryWebhookHandler.mapToEnglishCountry(it) }
            .toList()

        // then
        assertThat(translations[0]).isEqualTo("china")
        assertThat(translations[1]).isEqualTo("china")
        assertThat(translations[2]).isEqualTo("poland")
        assertThat(translations[3]).isEqualTo("poland")
        assertThat(translations[4]).isEqualTo("italy")
        assertThat(translations[5]).isEqualTo("italy")
        assertThat(translations[6]).isEqualTo("spain")
        assertThat(translations[7]).isEqualTo("germany")
        assertThat(translations[8]).isEqualTo("france")
        assertThat(translations[9]).isEqualTo("japan")
        assertThat(translations[10]).isEqualTo("czechia")
        assertThat(translations[11]).isEqualTo("usa")
    }

    @Test
    fun `Should return null if country not found`() {
        // given
        val name = "FFFF"

        // when
        val translatedName = countryWebhookHandler.mapToEnglishCountry(name)

        // then
        assertThat(translatedName).isNull()
    }
}
