package pl.manager

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.web.client.RestTemplate
import pl.config.CasesProperties

internal class CasesManagerTest {
    private val restTemplate: RestTemplate = mock(RestTemplate::class.java)
    private val casesProperties: CasesProperties = CasesProperties(
        apiUrl = "apiUrl", cron = "0 0 8-23 ? * * *"
    )
    private val casesManager: CasesManager = CasesManager(restTemplate, casesProperties)

    @Test
    fun `Should extract country name from webhook request`() {
        // when

    }
}
