package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cases")
data class CasesProperties(
    var host: String?,
    var cron: String?
)
