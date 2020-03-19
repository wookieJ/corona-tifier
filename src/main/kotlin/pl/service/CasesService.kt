package pl.service

import org.apache.http.client.HttpResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import pl.model.CountryInformation
import pl.logger

@Component
class CasesService(val restTemplate: RestTemplate) {
    fun getCountryInformation(country: String): CountryInformation {
        try {
            val countryResponse: ResponseEntity<CountryInformation> = restTemplate.getForEntity("https://corona.lmao.ninja/countries/$country", CountryInformation::class.java)
            if (!countryResponse.statusCode.is2xxSuccessful) {
                throw HttpResponseException(countryResponse.statusCodeValue, "[${countryResponse.statusCode}] There was a problem while getting country $country: ${countryResponse.body}")
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
            val countriesResponse: ResponseEntity<Array<CountryInformation>> = restTemplate.getForEntity("https://corona.lmao.ninja/countries", Array<CountryInformation>::class.java)
            if (!countriesResponse.statusCode.is2xxSuccessful) {
                throw HttpResponseException(countriesResponse.statusCodeValue, "[${countriesResponse.statusCode}] There was a problem while getting countries: ${countriesResponse.body}")
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
    }
}
