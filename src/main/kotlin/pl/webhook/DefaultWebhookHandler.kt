package pl.webhook

import org.springframework.stereotype.Component
import pl.entity.Message
import pl.entity.MessageRequest
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger

@Component
class DefaultWebhookHandler : WebhookCommonExtractor() {
    fun handle(webhookRequest: WebhookRequest): MessageRequest {
        logger.info("Handling default intent")
        val recipientId: String = extractRecipientId(webhookRequest)
        val messageContent = DEFAULT_MESSAGE
        return MessageRequest(Recipient(recipientId), Message(messageContent))
    }

    companion object {
        private val logger by logger()
        const val DEFAULT_MESSAGE = "Nie rozumiem"
    }
}
