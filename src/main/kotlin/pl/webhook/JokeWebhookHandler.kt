package pl.webhook

import org.springframework.stereotype.Component
import pl.entity.Message
import pl.entity.MessageRequest
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger
import pl.manager.JokesManager

@Component
class JokeWebhookHandler(private val jokesManager: JokesManager) : WebhookCommonExtractor() {
    fun handle(webhookRequest: WebhookRequest): MessageRequest {
        logger.info("Handling joke intent")
        val recipientId: String =  extractRecipientId(webhookRequest)
        val messageContent: String = createMessageContent()
        return MessageRequest(Recipient(recipientId), Message(messageContent))
    }

    private fun createMessageContent(): String {
        return jokesManager.getRandomJoke()
    }

    companion object {
        private val logger by logger()
    }
}
