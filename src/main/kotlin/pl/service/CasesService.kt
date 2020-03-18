package pl.service

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.model.CountryInformation
import pl.logger

@Component
class CasesService(val restTemplate: RestTemplate) {
    fun getCountryInformation(country: String): CountryInformation {
        val data =
            restTemplate.getForEntity("https://corona.lmao.ninja/countries/$country", CountryInformation::class.java)
        logger.info("Found ${data.body!!.country} cases: ${data.body!!}")
        return data.body!!
    }

    fun getCountriesInformation(): Array<CountryInformation> {
        val data =
            restTemplate.getForEntity("https://corona.lmao.ninja/countries", Array<CountryInformation>::class.java)
        logger.info("Found ${data.body!!.size} countries cases")
        return data.body!!
    }

    companion object {
        private val logger by logger()
    }
}
