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
        val name = "Polska"

        // when
        val translatedName = countryWebhookHandler.mapToEnglishCountry(name)

        // then
        assertThat(translatedName).isEqualTo("poland")
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
