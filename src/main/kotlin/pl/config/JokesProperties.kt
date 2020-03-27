package pl.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jokes")
data class JokesProperties(
    var apiUrl: String?
)

