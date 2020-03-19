package pl.jobs

import org.springframework.scheduling.annotation.Scheduled
import pl.logger
import pl.model.CountryInformation
import pl.service.CasesService

class CasesScheduler(
    private val delay: Long, private val casesService: CasesService
) {
    @Scheduled(fixedDelay = 60_000)
    fun getCountriesScheduler() {
        val countriesCases: Array<CountryInformation> = casesService.getCountriesInformation()
        val polandCases: CountryInformation = casesService.getCountryInformation("poland")
        logger.info("Found ${countriesCases.size} cases, Poland: ${polandCases.cases}")
    }

    companion object {
        private val logger by logger()
    }
}
