package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cases")
data class CasesProperties(
    var apiUrl: String?,
    var cron: String?
)
