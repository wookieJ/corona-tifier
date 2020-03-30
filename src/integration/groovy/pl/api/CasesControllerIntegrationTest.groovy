package pl.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import pl.WireMockIntegrationSpec
import pl.stubs.CasesResponses
import pl.stubs.CasesStubs

class CasesControllerIntegrationTest extends WireMockIntegrationSpec {

    @Autowired
    CasesController casesController

    def "Should return countries cases"() {
        given:
        CasesStubs.getCountriesStub(CasesResponses.countriesResponses())

        when:
        def countries = casesController.getCountriesInformation()

        then:
        countries.statusCode == HttpStatus.OK
        countries.body[0].country == "USA"
        countries.body[0].cases == 145542
        countries.body[0].todayCases == 2051
        countries.body[0].deaths == 2616
        countries.body[0].todayDeaths == 33
        countries.body[0].recovered == 4579
        countries.body[1].country == "Spain"
        countries.body[1].cases == 85195
        countries.body[1].todayCases == 5085
        countries.body[1].deaths == 7340
        countries.body[1].todayDeaths == 537
        countries.body[1].recovered == 16780
    }

    def "Should return poland country cases"() {
        given:
        def countryName = "Poland"
        CasesStubs.getCountryStub(countryName, CasesResponses.polandCasesResponse())

        when:
        def countryCases = casesController.getCountryInformation(countryName)

        then:
        countryCases.statusCode == HttpStatus.OK
        countryCases.body.country == "Poland"
        countryCases.body.cases == 1984
        countryCases.body.todayCases == 122
        countryCases.body.deaths == 26
        countryCases.body.todayDeaths == 4
        countryCases.body.recovered == 7
    }
}
