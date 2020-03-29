package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.entity.Context
import pl.entity.MessengerResponse
import pl.entity.QueryResult
import pl.entity.WebhookRequest
import pl.manager.CasesManager
import pl.model.CountryInformation
import pl.translation.Translator

internal class CountryWebhookHandlerTest {
    private val casesManager: CasesManager = mock(CasesManager::class.java)
    private val translator: Translator = mock(Translator::class.java)
    private val countryWebhookHandler = CountryWebhookHandler(casesManager, translator)

    @Test
    fun `Should extract country name from webhook request`() {
        // given
        val queryResult = QueryResult(
            parameters = mapOf(
                CountryWebhookHandler.COUNTRY_PARAMETER_NAME to "Polska"
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val extractedCountryName: String = countryWebhookHandler.extractCountryName(webhookRequest)

        // then
        assertThat(extractedCountryName).isEqualTo("Polska")
    }

    @Test
    fun `Should return empty string if country not found in webhook request`() {
        // given
        val queryResult = QueryResult(
            parameters = mapOf()
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val extractedCountryName: String = countryWebhookHandler.extractCountryName(webhookRequest)

        // then
        assertThat(extractedCountryName).isEmpty()
    }

    @Test
    fun `Should return empty string if parameters not found in webhook request`() {
        // given
        val queryResult = QueryResult()
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val extractedCountryName: String = countryWebhookHandler.extractCountryName(webhookRequest)

        // then
        assertThat(extractedCountryName).isEmpty()
    }

    @Test
    fun `Should return empty string if query results not found in webhook request`() {
        // given
        val webhookRequest = WebhookRequest(null, null, null, null)

        // when
        val extractedCountryName: String = countryWebhookHandler.extractCountryName(webhookRequest)

        // then
        assertThat(extractedCountryName).isEmpty()
    }

    @Test
    fun `Should create message response with country cases from webhook request`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
                )
                )
            ), parameters = mapOf(
            CountryWebhookHandler.COUNTRY_PARAMETER_NAME to "Polska"
        )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)
        `when`(casesManager.getCountryInformation("poland")).thenReturn(
            CountryInformation(
                country = "Poland", cases = 1000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3
            )
        )
        `when`(translator.mapToEnglishCountry("Polska")).thenReturn("poland")

        // when
        val messengerResponse: MessengerResponse = countryWebhookHandler.handle(webhookRequest)

        // then
        assertThat(messengerResponse.recipient.id).isEqualTo("user_id")
        assertThat(messengerResponse.message.text).isEqualTo(
            """
            Liczba przypadków : *1000*
            Liczba śmierci : *10*
            Dzisiaj przybyło : *100*
            Dzisiaj zmarło : *1*
            Uzdrowionych : *10*
            """.trimIndent()
        )
    }

    @Test
    fun `Should create message response with country not found in webhook request`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
                )
                )
            ), parameters = mapOf()
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val messengerResponse: MessengerResponse = countryWebhookHandler.handle(webhookRequest)

        // then
        assertThat(messengerResponse.recipient.id).isEqualTo("user_id")
        assertThat(messengerResponse.message.text).isEqualTo(CountryWebhookHandler.COUNTRY_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `Should create message response with invalid country in webhook request`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
                )
                )
            ), parameters = mapOf(
            CountryWebhookHandler.COUNTRY_PARAMETER_NAME to "NotExistingCountry"
        )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val messengerResponse: MessengerResponse = countryWebhookHandler.handle(webhookRequest)

        // then
        assertThat(messengerResponse.recipient.id).isEqualTo("user_id")
        assertThat(messengerResponse.message.text).isEqualTo(CountryWebhookHandler.COUNTRY_CANNOT_TRANSLATE_MESSAGE)
    }
}
