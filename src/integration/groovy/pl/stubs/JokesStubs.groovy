package pl.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.springframework.http.HttpStatus.OK

class JokesStubs {
    static def getRandomJokeStub(def joke) {
        stubFor(get(urlEqualTo("/losuj")).willReturn(
                aResponse()
                        .withStatus(OK.value())
                        .withBody("${joke}")
        ))
    }
}
