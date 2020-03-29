package pl.webhook

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import pl.entity.Context
import pl.entity.QueryResult
import pl.entity.WebhookRequest

internal class WebhookCommonExtractorTest {

    private val webhookCommonExtractor: WebhookCommonExtractor = WebhookCommonExtractor()

    @Test
    fun `Should extract recipient id from webhook request`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME to "user_id"
                )
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // when
        val recipientId: String = webhookCommonExtractor.extractRecipientId(webhookRequest)

        // then
        assertThat(recipientId).isEqualTo("user_id")
    }

    @Test
    fun `Should throw exception if query results not found`() {
        // given
        val webhookRequest = WebhookRequest(null, null, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage(WebhookCommonExtractor.CONTEXT_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `Should throw exception if contexts in query result not found`() {
        // given
        val queryResult = QueryResult()
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage(WebhookCommonExtractor.CONTEXT_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `Should throw exception if context in query result not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(Context())
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
    }

    @Test
    fun `Should throw exception if country context in query result not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = "Another_context"
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage("Collection contains no element matching the predicate.")
    }

    @Test
    fun `Should throw exception if recipient id not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME, parameters = mapOf(
                    "parameter" to "value"
                )
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage(WebhookCommonExtractor.RECIPIENT_ID_NOT_FOUND_MESSAGE)
    }

    @Test
    fun `Should throw exception if parameters of context not found`() {
        // given
        val queryResult = QueryResult(
            outputContexts = listOf(
                Context(
                    name = WebhookCommonExtractor.CONTEXT_NAME
                )
            )
        )
        val webhookRequest = WebhookRequest(null, queryResult, null, null)

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { webhookCommonExtractor.extractRecipientId(webhookRequest) }
            .withMessage(WebhookCommonExtractor.RECIPIENT_ID_NOT_FOUND_MESSAGE)
    }
}
