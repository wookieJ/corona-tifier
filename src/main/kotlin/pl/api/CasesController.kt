package pl.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.logger
import pl.model.CountryInformation
import pl.service.CasesService

@RestController
class CasesController(val casesService: CasesService) {

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

    companion object {
        private val logger by logger()
    }
}
