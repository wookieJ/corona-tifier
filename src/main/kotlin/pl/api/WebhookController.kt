package pl.api

import org.apache.http.client.HttpResponseException
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.entity.WebhookRequest
import pl.logger
import pl.manager.MessagesManager
import pl.webhook.CountryWebhookHandler
import pl.webhook.DefaultWebhookHandler
import pl.webhook.JokeWebhookHandler

@RestController
class WebhookController(
    val environment: Environment,
    val countryWebhookHandler: CountryWebhookHandler,
    val defaultWebhookHandler: DefaultWebhookHandler,
    val jokeWebhookHandler: JokeWebhookHandler,
    val messagesManager: MessagesManager
) {

    @PostMapping(value = ["/webhook"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun webHookEvent(@RequestBody webhookRequest: WebhookRequest) {
        logger.debug("POST /webhook with body: $webhookRequest")
        val accessToken: String = environment[ACCESS_TOKEN_ENV_NAME] ?: throw Exception("Access token not found")
        val intentType = webhookRequest.queryResult?.intent?.displayName?.toLowerCase() ?: throw Exception("Intent type not recognised")

        logger.info("Intent type $intentType found")
        val webhookResponse = when (intentType) {
            "country cases" -> countryWebhookHandler.handle(webhookRequest)
            "joke" -> jokeWebhookHandler.handle(webhookRequest)
            else -> defaultWebhookHandler.handle(webhookRequest)
        }

        messagesManager.sendMessage(accessToken, webhookResponse)
    }

    @ExceptionHandler
    fun handleException(exception: HttpResponseException): ResponseEntity<String> {
        var message = exception.message
        if (exception.message == null) {
            message = "HttpResponseException with status code ${exception.statusCode} was thrown while getting countries cases"
        }
        return ResponseEntity(message!!, HttpStatus.valueOf(exception.statusCode))
    }

    @ExceptionHandler
    fun handleException(exception: Exception): ResponseEntity<String> {
        var message = exception.message
        if (exception.message == null) {
            message = "Exception was thrown while getting country cases"
        }
        return ResponseEntity(message!!, HttpStatus.SERVICE_UNAVAILABLE)
    }

    companion object {
        private val logger by logger()
        private const val ACCESS_TOKEN_ENV_NAME = "ACCESS_TOKEN"
    }
}
