package pl.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.logger
import pl.model.CountryInformation
import pl.manager.CasesManager

@RestController
class CasesController(val casesService: CasesManager) {

    @GetMapping("/countries")
    fun getCountriesInformation(): ResponseEntity<Array<CountryInformation>> {
        logger.info("GET /countries")
        return ResponseEntity.ok(casesService.getCountriesInformation())
    }

    @GetMapping("/countries/{country}")
    fun getCountryInformation(@PathVariable country: String): ResponseEntity<CountryInformation> {
        logger.info("GET /countries/$country")
        return ResponseEntity.ok(casesService.getCountryInformation(country))
    }

    companion object {
        private val logger by logger()
    }
}
