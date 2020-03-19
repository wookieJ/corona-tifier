package pl.api

import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.logger
import pl.model.CountryInformation
import pl.service.CasesService

@RestController
class WebhookController(val casesService: CasesService) {

    @PostMapping("/webhook")
    fun webhookEvent(@RequestBody event: Any): Any {
        logger.info("POST /webhook $event")
        return event
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
    }
}
