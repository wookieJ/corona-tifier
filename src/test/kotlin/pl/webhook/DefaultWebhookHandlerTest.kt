package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.entity.Context
import pl.entity.MessageRequest
import pl.entity.QueryResult
import pl.entity.WebhookRequest

internal class DefaultWebhookHandlerTest {

    private val defaultWebhookHandler = DefaultWebhookHandler()

    @Test
    fun `Should create message response with default message`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "recipient_id"
                )
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val messageRequest: MessageRequest = defaultWebhookHandler.handle(webhookRequest)

        // then
        assertThat(messageRequest.recipient.id).isEqualTo("recipient_id")
        assertThat(messageRequest.message.text).isEqualTo(DefaultWebhookHandler.DEFAULT_MESSAGE)
    }
}
