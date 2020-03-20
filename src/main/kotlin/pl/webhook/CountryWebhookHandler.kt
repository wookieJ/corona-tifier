package pl.webhook

import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Component
import pl.entity.Context
import pl.entity.Message
import pl.entity.MessengerResponse
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger
import pl.manager.CasesManager

@Component
class CountryWebhookHandler(val casesManager: CasesManager) : WebhookHandler {
    override fun handle(webhookRequest: WebhookRequest): MessengerResponse {
        val recipientId: String = extractRecipientId(webhookRequest)
        val countryName: String = extractCountryName(webhookRequest)
        val messageContent: String = extractMessageContent(countryName)
        return MessengerResponse(Recipient(recipientId), Message(messageContent))
    }

    private fun extractMessageContent(countryName: String): String {
        return if (countryName.isNotEmpty()) {
            val countryNameTranslated: String? = mapToEnglishCountry(countryName)
            if (countryNameTranslated != null) {
                casesManager
                    .getCountryInformation(countryNameTranslated)
                    .toHumanizedFormat()
            } else {
                COUNTRY_CANNOT_TRANSLATE_MESSAGE
            }
        } else {
            COUNTRY_NOT_FOUND_MESSAGE
        }
    }

    private fun extractCountryName(webhookRequest: WebhookRequest): String {
        val countryName = webhookRequest.queryResult?.parameters?.get(COUNTRY_PARAMETER_NAME) ?: Strings.EMPTY
        logger.info("Found country name $countryName")
        return countryName.toString()
    }

    private fun extractRecipientId(webhookRequest: WebhookRequest): String {
        val context: Context = webhookRequest.queryResult?.outputContexts?.first { it.name?.contains(CONTEXT_NAME)!! } ?: throw Exception("Context not found")
        val recipientId: Any? = context.parameters?.get(RECIPIENT_ID_PARAMETER_NAME) ?: throw Exception("Recipient ID not found")
        logger.info("Found recipientId: $recipientId")
        return recipientId.toString()
    }

    private fun mapToEnglishCountry(countryOriginal: String?): String? {
        logger.info("Mapping country $countryOriginal")
        return when (countryOriginal) {
            "Chiny" -> "china"
            "Polska" -> "poland"
            "Polsce" -> "poland"
            "Włochy" -> "italy"
            "Włoszech" -> "italy"
            "Hiszpania" -> "spain"
            "Niemcy" -> "germany"
            "Francja" -> "france"
            "Japonia" -> "japan"
            "Czechy" -> "czechia"
            else -> null
        }
    }

    companion object {
        private val logger by logger()
        private const val CONTEXT_NAME = "projects/sekretarztwo-jxstmq/agent/sessions"
        private const val COUNTRY_PARAMETER_NAME = "projects/sekretarztwo-jxstmq/agent/sessions"
        private const val RECIPIENT_ID_PARAMETER_NAME = "facebook_sender_id"
        private const val COUNTRY_CANNOT_TRANSLATE_MESSAGE = "Nie mam tego kraju w bazie jeszcze :( "
        private const val COUNTRY_NOT_FOUND_MESSAGE = "Podaj nazwę kraju mordo"
    }
}
