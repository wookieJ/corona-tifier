package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import pl.entity.Context
import pl.entity.QueryResult
import pl.entity.WebhookRequest

internal class WebhookCommonExtractorTest {

    @Test
    fun `Should extract recipient id from webhook request`() {
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

        // when
        val recipientId = WebhookCommonExtractor.extractRecipientId(webhookRequest)

        // then
        assertThat(recipientId).isEqualTo("user_id")
    }

    @Test
    fun `Should throw exception if context in query result not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = "Another_context"
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when

        // then
        assertThatExceptionOfType(Exception::class.java)
            .isThrownBy { WebhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage("Context not found")
    }

    @Test
    fun `Should throw exception if recipient id not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME,
                    parameters = mapOf(
                        "parameter" to "value"
                    )
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when

        // then
        assertThatExceptionOfType(Exception::class.java)
            .isThrownBy { WebhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage("Recipient ID not found")
    }
}
