package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import pl.entity.Context
import pl.entity.MessengerResponse
import pl.entity.QueryResult
import pl.entity.WebhookRequest
import pl.manager.JokesManager

internal class JokeWebhookHandlerTest {

    private val jokesManager = mock(JokesManager::class.java)
    private val jokeWebhookHandler = JokeWebhookHandler(jokesManager)

    @Test
    fun `Should create message response with joke from webhook request`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME,
                    parameters = mapOf(
                        WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
                    )
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)
        `when`(jokesManager.getRandomJoke()).thenReturn("Very funny joke")

        // when
        val messengerResponse: MessengerResponse = jokeWebhookHandler.handle(webhookRequest)

        // then
        assertThat(messengerResponse.recipient.id).isEqualTo("user_id")
        assertThat(messengerResponse.message.text).isEqualTo("Very funny joke")
    }
}
