package pl.webhook

import pl.entity.MessengerResponse
import pl.entity.WebhookRequest

interface WebhookHandler {
    fun handle(webhookRequest: WebhookRequest): MessengerResponse
}
