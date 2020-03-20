package pl.api

import com.google.actions.api.ActionContext
import com.google.actions.api.ActionRequest
import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.logger
import pl.manager.CasesManager

@RestController
class WebhookController(val casesService: CasesManager) {

    @PostMapping("/webhook")
    fun webhookEvent(@RequestBody actionRequest: ActionRequest): ActionRequest {
        val context: ActionContext = actionRequest.getContext(CONTEXT_NAME) ?: throw Exception("Context not found")
        val userId: Any? = context.parameters?.get("facebook_sender_id") ?: throw Exception("User ID not found")
        logger.info("POST /webhook from $userId")
        return actionRequest
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
        private const val CONTEXT_NAME = "projects/sekretarztwo-jxstmq/agent/sessions/7db067e2-9bb3-4de4-b2b9-5453d5706f6d/contexts/generic"
    }
}
