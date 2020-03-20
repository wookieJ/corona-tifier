package pl.jobs

import org.springframework.scheduling.annotation.Scheduled
import pl.logger
import pl.model.CountryInformation
import pl.manager.CasesManager

class CasesScheduler(
    private val cron: String?,
    private val casesService: CasesManager
) {
    // TODO - cron value from properties
    @Scheduled(cron = "0 0 8-23 ? * *")
    fun getCountriesScheduler() {
        val countriesCases: Array<CountryInformation> = casesService.getCountriesInformation()
        val polandCases: CountryInformation = casesService.getCountryInformation("poland")
        logger.info("Found ${countriesCases.size} cases, Poland: ${polandCases.cases}")
    }

    companion object {
        private val logger by logger()
    }
}
