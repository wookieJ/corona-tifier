package pl.webhook

import org.springframework.stereotype.Component
import pl.entity.Message
import pl.entity.MessengerResponse
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger
import pl.manager.JokesManager

@Component
class JokeWebhookHandler(val jokesManager: JokesManager) {
    fun handle(webhookRequest: WebhookRequest): MessengerResponse {
        logger.info("Handling joke intent")
        val recipientId: String = WebhookCommonExtractor.extractRecipientId(webhookRequest)
        val messageContent: String = createMessageContent()
        return MessengerResponse(Recipient(recipientId), Message(messageContent))
    }

    private fun createMessageContent(): String {
        return jokesManager.getRandomJoke()
    }

    companion object {
        private val logger by logger()
    }
}
