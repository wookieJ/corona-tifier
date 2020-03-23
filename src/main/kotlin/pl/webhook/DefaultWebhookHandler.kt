package pl.webhook

import org.springframework.stereotype.Component
import pl.entity.Context
import pl.entity.Message
import pl.entity.MessengerResponse
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger

@Component
class DefaultWebhookHandler {
    fun handle(webhookRequest: WebhookRequest): MessengerResponse {
        val recipientId: String = extractRecipientId(webhookRequest)
        val messageContent = "Nie rozumiem"
        return MessengerResponse(Recipient(recipientId), Message(messageContent))
    }

    private fun extractRecipientId(webhookRequest: WebhookRequest): String {
        val context: Context = webhookRequest.queryResult?.outputContexts?.first { it.name?.contains(CONTEXT_NAME)!! } ?: throw Exception("Context not found")
        val recipientId: Any? = context.parameters?.get(RECIPIENT_ID_PARAMETER_NAME) ?: throw Exception("Recipient ID not found")
        logger.info("Found recipientId: $recipientId")
        return recipientId.toString()
    }

    companion object {
        private val logger by logger()
        private const val CONTEXT_NAME = "projects/sekretarztwo-jxstmq/agent/sessions"
        private const val RECIPIENT_ID_PARAMETER_NAME = "facebook_sender_id"
    }
}
