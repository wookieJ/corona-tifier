package pl

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class BaseIntegrationSpec extends Specification {

    @Value('${local.server.port}')
    protected int port

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }
}
