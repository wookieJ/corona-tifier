package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("cases")
data class CasesSchedulerProperties(
    var delayInMinutes: Long = 1
)
