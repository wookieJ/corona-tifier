package pl.manager

import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import pl.config.CasesProperties
import pl.model.CountryInformation
import pl.logger

@Service
class CasesManager(
    val restTemplate: RestTemplate, val casesProperties: CasesProperties
) {
    fun getCountryInformation(country: String): CountryInformation {
        try {
            val casesHost: String? = casesProperties.apiUrl ?: throw Exception("Cases apiUrl not defined")
            val countryResponse: ResponseEntity<CountryInformation> = restTemplate.getForEntity("$casesHost/countries/$country", CountryInformation::class.java)
            if (!countryResponse.statusCode.is2xxSuccessful) {
                throw HttpResponseException(countryResponse.statusCodeValue, "[${countryResponse.statusCode}] ${countryResponse.body}")
            }
            logger.info("Found ${countryResponse.body!!.country} cases: ${countryResponse.body!!}")
            return countryResponse.body!!
        } catch (httpException: HttpClientErrorException) {
            logger.error("Problems with getting country $country: ${httpException.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting country $country: ${httpException.message}")
        } catch (exception: Exception) {
            logger.error("Problems with getting country $country: ${exception.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting country $country: ${exception.message}")
        }
    }

    fun getCountriesInformation(): Array<CountryInformation> {
        try {
            val casesHost: String? = casesProperties.apiUrl ?: throw Exception(CASES_API_URL_NOT_FOUND_MESSAGE)
            val countriesResponse: ResponseEntity<Array<CountryInformation>> = restTemplate.getForEntity("$casesHost/countries", Array<CountryInformation>::class.java)
            if (!countriesResponse.statusCode.is2xxSuccessful) {
                throw HttpResponseException(countriesResponse.statusCodeValue, "[${countriesResponse.statusCode}] ${countriesResponse.body}")
            }
            logger.info("Found ${countriesResponse.body!!.size} countries cases")
            return countriesResponse.body!!
        } catch (httpException: HttpClientErrorException) {
            logger.error("Problems with getting countries: ${httpException.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting countries: ${httpException.message}")
        } catch (exception: Exception) {
            logger.error("Problems with getting countries: ${exception.message}")
            throw HttpResponseException(HttpStatus.NOT_FOUND.value(), "Problems with getting countries: ${exception.message}")
        }
    }

    companion object {
        private val logger by logger()
        const val CASES_API_URL_NOT_FOUND_MESSAGE = "Cases apiUrl not defined"
    }
}
