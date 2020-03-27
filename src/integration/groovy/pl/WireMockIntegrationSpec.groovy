package pl

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule

class WireMockIntegrationSpec extends BaseIntegrationSpec {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(8090)

    def cleanup() {
        WireMock.reset()
    }
}
