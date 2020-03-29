package pl.webhook

import pl.entity.Context
import pl.entity.WebhookRequest
import pl.logger

class WebhookCommonExtractor {
    companion object {
        private val logger by logger()
        const val CONTEXT_NAME = "projects/sekretarztwo-jxstmq/agent/sessions"
        const val RECIPIENT_ID_PARAMETER_NAME = "facebook_sender_id"

        fun extractRecipientId(webhookRequest: WebhookRequest): String {
            val context: Context?
            try {
                context = webhookRequest.queryResult?.outputContexts?.first { it.name?.contains(CONTEXT_NAME)!! }
            } catch (exception: Exception) {
                throw Exception("Context not found")
            }
            val recipientId: Any? = context!!.parameters?.get(RECIPIENT_ID_PARAMETER_NAME) ?: throw Exception("Recipient ID not found")
            logger.info("Found recipientId: $recipientId")
            return recipientId.toString()
        }
    }
}
