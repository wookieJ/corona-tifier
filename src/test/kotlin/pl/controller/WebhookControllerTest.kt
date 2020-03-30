package pl.controller

import org.apache.http.client.HttpResponseException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import pl.api.WebhookController
import pl.config.CasesProperties
import pl.entity.Context
import pl.entity.QueryResult
import pl.entity.WebhookRequest
import pl.manager.MessagesManager
import pl.model.CountryInformation
import pl.webhook.CountryWebhookHandler
import pl.webhook.DefaultWebhookHandler
import pl.webhook.JokeWebhookHandler
import pl.webhook.WebhookCommonExtractor

internal class WebhookControllerTest {
    val environment: Environment = mock(Environment::class.java)
    val countryWebhookHandler: CountryWebhookHandler = mock(CountryWebhookHandler::class.java)
    val defaultWebhookHandler: DefaultWebhookHandler=mock(DefaultWebhookHandler::class.java)
    val jokeWebhookHandler: JokeWebhookHandler=mock(JokeWebhookHandler::class.java)
    val messagesManager: MessagesManager= mock(MessagesManager::class.java)
    val webhookController:WebhookController= WebhookController(environment, countryWebhookHandler, defaultWebhookHandler, jokeWebhookHandler, messagesManager)

    @Test
    fun `Should send response message with country cases`() {
//        // given
//        val queryResult = QueryResult(
//            outputContexts = listOf(
//                Context(
//                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
//                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
//                )
//                )
//            ), parameters = mapOf(
//            CountryWebhookHandler.COUNTRY_PARAMETER_NAME to "Polska"
//        )
//        )
//        val webhookRequest = WebhookRequest(null, queryResult, null, null)
//        `when`(casesManager.getCountryInformation("poland")).thenReturn(
//            CountryInformation(
//                country = "Poland", cases = 1000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3
//            )
//        )
//
//        // when
//        val countriesCases: Array<CountryInformation> = webhookController.webHookEvent(webhookRequest)
//
//        // then
//        assertThat(countriesCases.size).isEqualTo(3)
//        assertThat(countriesCases).isEqualTo(
//            arrayOf(
//                CountryInformation(country = "Poland", cases = 1_000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3),
//                CountryInformation(
//                    country = "Italy", cases = 80_233, todayCases = 5_345, deaths = 5_433, todayDeaths = 567, recovered = 1_234, critical = 3_133
//                ), CountryInformation(country = "USA", cases = 101_202, todayCases = 9_234, deaths = 1_344, todayDeaths = 59, recovered = 344, critical = 98)
//            )
//        )
    }
}
