package pl.manager

import org.apache.http.client.HttpResponseException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.config.FacebookProperties
import pl.entity.MessengerResponse
import pl.logger

@Component
class MessagesManager(
    val restTemplate: RestTemplate,
    val facebookProperties: FacebookProperties
) {
    fun sendMessage(accessToken: String, messengerResponse: MessengerResponse) {
        logger.info("Sending message to ${messengerResponse.recipient}")
        val facebookHost = facebookProperties.apiUrl ?: throw Exception("Facebook API url not found")
        val facebookApiUrl = "$facebookHost?access_token=$accessToken"
        val response: ResponseEntity<Any> = restTemplate.postForEntity(facebookApiUrl, messengerResponse, Any::class.java)
        if (!response.statusCode.is2xxSuccessful) {
            logger.error("Problems with sending message to ${messengerResponse.recipient}: ${response.body}")
            throw HttpResponseException(response.statusCodeValue, "Problems with sending message to ${messengerResponse.recipient}: ${response.body}")
        }
        logger.info("Successfully sent message to ${messengerResponse.recipient}")
    }

    companion object {
        private val logger by logger()
    }
}
