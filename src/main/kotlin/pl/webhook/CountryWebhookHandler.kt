package pl.webhook

import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Component
import pl.entity.Message
import pl.entity.MessengerResponse
import pl.entity.Recipient
import pl.entity.WebhookRequest
import pl.logger
import pl.manager.CasesManager
import pl.translation.Translator

@Component
class CountryWebhookHandler(
    val casesManager: CasesManager, val translator: Translator
) {
    fun handle(webhookRequest: WebhookRequest): MessengerResponse {
        logger.info("Handling country intent")
        val recipientId: String = WebhookCommonExtractor.extractRecipientId(webhookRequest)
        val countryName: String = extractCountryName(webhookRequest)
        val messageContent: String = createMessageContent(countryName)
        return MessengerResponse(Recipient(recipientId), Message(messageContent))
    }

    fun createMessageContent(countryName: String): String {
        return if (countryName.isNotEmpty()) {
            val countryNameTranslated: String? = translator.mapToEnglishCountry(countryName)
            if (countryNameTranslated != null) {
                casesManager.getCountryInformation(countryNameTranslated).toHumanizedFormat()
            } else {
                COUNTRY_CANNOT_TRANSLATE_MESSAGE
            }
        } else {
            COUNTRY_NOT_FOUND_MESSAGE
        }
    }

    fun extractCountryName(webhookRequest: WebhookRequest): String {
        val countryName = webhookRequest.queryResult?.parameters?.get(COUNTRY_PARAMETER_NAME) ?: return Strings.EMPTY
        logger.info("Found country name $countryName")
        return countryName.toString()
    }

    companion object {
        private val logger by logger()
        const val COUNTRY_CANNOT_TRANSLATE_MESSAGE = "Nie mam tego kraju w bazie jeszcze :( "
        const val COUNTRY_NOT_FOUND_MESSAGE = "Podaj nazwę kraju mordo"
        const val COUNTRY_PARAMETER_NAME = "country"
    }
}
