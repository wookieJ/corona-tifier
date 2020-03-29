package pl.manager

import org.apache.http.client.HttpResponseException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.config.FacebookProperties
import pl.entity.MessageRequest
import pl.logger

@Service
class MessagesManager(
    val restTemplate: RestTemplate,
    val facebookProperties: FacebookProperties
) {
    fun sendMessage(accessToken: String, messageRequest: MessageRequest): Any {
        logger.info("Sending message to ${messageRequest.recipient}")
        val facebookHost = facebookProperties.apiUrl ?: throw Exception("Facebook API url not found")
        val facebookApiUrl = "$facebookHost?access_token=$accessToken"
        val response: ResponseEntity<Any> = restTemplate.postForEntity(facebookApiUrl, messageRequest, Any::class.java)
        if (!response.statusCode.is2xxSuccessful) {
            logger.error("Problems with sending message to ${messageRequest.recipient.id}: ${response.body}")
            throw HttpResponseException(response.statusCodeValue, "Problems with sending message to ${messageRequest.recipient.id}: ${response.body}")
        }
        logger.info("Successfully sent message to ${messageRequest.recipient.id}")
        return response.body!!
    }

    companion object {
        private val logger by logger()
    }
}
