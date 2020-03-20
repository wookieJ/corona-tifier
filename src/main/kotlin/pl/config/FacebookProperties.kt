package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("facebook")
data class FacebookProperties(
    var apiUrl: String?
)
