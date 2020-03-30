package pl.manager

import org.apache.http.client.HttpResponseException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import pl.config.CasesProperties
import pl.model.CountryInformation

internal class CasesManagerTest {
    private val restTemplate: RestTemplate = mock(RestTemplate::class.java)
    private val casesProperties: CasesProperties = CasesProperties(
        apiUrl = "http://cases"
    )
    private val casesManager: CasesManager = CasesManager(restTemplate, casesProperties)

    @Test
    fun `Should return all countries cases`() {
        // given
        `when`(
            restTemplate.getForEntity(eq("http://cases/countries"), eq(Array<CountryInformation>::class.java))
        ).thenReturn(
            ResponseEntity(
                arrayOf(
                    CountryInformation(country = "Poland", cases = 1_000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3),
                    CountryInformation(
                        country = "Italy", cases = 80_233, todayCases = 5_345, deaths = 5_433, todayDeaths = 567, recovered = 1_234, critical = 3_133
                    ),
                    CountryInformation(country = "USA", cases = 101_202, todayCases = 9_234, deaths = 1_344, todayDeaths = 59, recovered = 344, critical = 98)
                ), HttpStatus.OK
            )
        )

        // when
        val countriesCases: Array<CountryInformation> = casesManager.getCountriesInformation()

        // then
        assertThat(countriesCases.size).isEqualTo(3)
        assertThat(countriesCases).isEqualTo(
            arrayOf(
                CountryInformation(country = "Poland", cases = 1_000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3),
                CountryInformation(
                    country = "Italy", cases = 80_233, todayCases = 5_345, deaths = 5_433, todayDeaths = 567, recovered = 1_234, critical = 3_133
                ), CountryInformation(country = "USA", cases = 101_202, todayCases = 9_234, deaths = 1_344, todayDeaths = 59, recovered = 344, critical = 98)
            )
        )
    }

    @Test
    fun `Should throw exception when there is no url in case properties while getting all countries cases`() {
        // given
        casesProperties.apiUrl = null

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { casesManager.getCountriesInformation() }
            .withMessage("Problems with getting countries: ${CasesManager.CASES_API_URL_NOT_FOUND_MESSAGE}")
    }

    @Test
    fun `Should throw exception when case response status code is not 2xx while getting all countries cases`() {
        // given
        `when`(
            restTemplate.getForEntity(eq("http://cases/countries"), eq(Array<CountryInformation>::class.java))
        ).thenReturn(
            ResponseEntity(HttpStatus.NOT_FOUND)
        )

        // then
        assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { casesManager.getCountriesInformation() }
            .withMessageStartingWith("Problems with getting countries: [${HttpStatus.NOT_FOUND}]")
    }

    @Test
    fun `Should catch client exception while getting all countries cases`() {
        // given
        `when`(
            restTemplate.getForEntity(eq("http://cases/countries"), eq(Array<CountryInformation>::class.java))
        ).thenThrow(HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server unavailable"))

        // then
        assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { casesManager.getCountriesInformation() }.withMessageStartingWith(
            "Problems with getting countries: 500 Server unavailable"
        )
    }

    @Test
    fun `Should return country cases`() {
        // given
        val countryName = "Poland"
        `when`(
            restTemplate.getForEntity(eq("http://cases/countries/$countryName"), eq(CountryInformation::class.java))
        ).thenReturn(
            ResponseEntity(
                CountryInformation(country = countryName, cases = 1_000, todayCases = 100, deaths = 10, todayDeaths = 1, recovered = 10, critical = 3),
                HttpStatus.OK
            )
        )

        // when
        val countriesCases: CountryInformation = casesManager.getCountryInformation(countryName)

        // then
        assertThat(countriesCases.country).isEqualTo(countryName)
        assertThat(countriesCases.cases).isEqualTo(1_000)
        assertThat(countriesCases.todayCases).isEqualTo(100)
        assertThat(countriesCases.deaths).isEqualTo(10)
        assertThat(countriesCases.todayDeaths).isEqualTo(1)
        assertThat(countriesCases.recovered).isEqualTo(10)
        assertThat(countriesCases.critical).isEqualTo(3)
    }

    @Test
    fun `Should throw exception when there is no url in case properties while getting country cases`() {
        // given
        val countryName = "Poland"
        casesProperties.apiUrl = null

        // then
        assertThatExceptionOfType(Exception::class.java).isThrownBy { casesManager.getCountryInformation(countryName) }
            .withMessage("Problems with getting country $countryName: ${CasesManager.CASES_API_URL_NOT_FOUND_MESSAGE}")
    }

    @Test
    fun `Should throw exception when case response status code is not 2xx while getting country cases`() {
        // given
        val countryName = "Poland"

        `when`(
            restTemplate.getForEntity(eq("http://cases/countries/$countryName"), eq(CountryInformation::class.java))
        ).thenReturn(
            ResponseEntity(HttpStatus.NOT_FOUND)
        )

        // then
        assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { casesManager.getCountryInformation(countryName) }
            .withMessageStartingWith("Problems with getting country $countryName: [${HttpStatus.NOT_FOUND}]")
    }

    @Test
    fun `Should catch client exception while getting country cases`() {
        // given
        val countryName = "Poland"

        `when`(
            restTemplate.getForEntity(eq("http://cases/countries/$countryName"), eq(CountryInformation::class.java))
        ).thenThrow(HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server unavailable"))

        // then
        assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { casesManager.getCountryInformation(countryName) }.withMessageStartingWith(
            "Problems with getting country $countryName: 500 Server unavailable"
        )
    }
}
