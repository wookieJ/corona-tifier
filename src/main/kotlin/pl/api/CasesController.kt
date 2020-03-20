package pl.api

import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.logger
import pl.model.CountryInformation
import pl.manager.CasesManager

@RestController
class CasesController(val casesService: CasesManager) {

    @GetMapping("/countries")
    fun getCountriesInformation(): Array<CountryInformation> {
        logger.info("GET /countries")
        return casesService.getCountriesInformation()
    }

    @GetMapping("/countries/{country}")
    fun getCountryInformation(@PathVariable country: String): CountryInformation {
        logger.info("GET /countries/$country")
        return casesService.getCountryInformation(country)
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
