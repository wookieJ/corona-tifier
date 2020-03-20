package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cases")
data class CasesSchedulerProperties(
    var cron: String = ""
)
