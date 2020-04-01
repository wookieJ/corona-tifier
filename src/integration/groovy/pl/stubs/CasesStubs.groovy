package pl.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.get

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class CasesStubs {
    static def getCountriesStub(def countries) {
        stubFor(get(urlEqualTo("/countries")).willReturn(
                aResponse()
                        .withStatus(OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody("${countries}")
        ))
    }

    static def getCountryStub(def countryName, def country) {
        stubFor(get(urlEqualTo("/countries/${countryName}")).willReturn(
                aResponse()
                        .withStatus(OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody("${country}")
        ))
    }

    static def countriesNotFoundStub() {
        stubFor(get(urlEqualTo("/countries")).willReturn(
                aResponse()
                        .withStatus(NOT_FOUND.value())
                        .withBody("Countries not found")
        ))
    }
}
