package pl.webhook

import org.springframework.stereotype.Component
import pl.entity.Context
import pl.entity.WebhookRequest
import pl.logger

@Component
class WebhookCommonExtractor {
    fun extractRecipientId(webhookRequest: WebhookRequest): String {
        val context: Context?
        try {
            context = webhookRequest.queryResult?.outputContexts?.first { it.name?.contains(CONTEXT_NAME)!! }
            if (context == null) {
                throw Exception(CONTEXT_NOT_FOUND_MESSAGE)
            }
        } catch (exception: Exception) {
            throw Exception(exception.message)
        }
        val recipientId: Any? = context.parameters?.get(RECIPIENT_ID_PARAMETER_NAME) ?: throw Exception(RECIPIENT_ID_NOT_FOUND_MESSAGE)
        logger.info("Found recipientId: $recipientId")
        return recipientId.toString()
    }

    companion object {
        private val logger by logger()
        const val CONTEXT_NAME = "projects/sekretarztwo-jxstmq/agent/sessions"
        const val RECIPIENT_ID_PARAMETER_NAME = "facebook_sender_id"
        const val CONTEXT_NOT_FOUND_MESSAGE = "Context not found"
        const val RECIPIENT_ID_NOT_FOUND_MESSAGE = "Recipient ID not found"
    }
}
