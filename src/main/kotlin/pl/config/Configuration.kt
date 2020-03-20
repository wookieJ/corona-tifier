package pl.config

import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import pl.jobs.CasesScheduler
import pl.manager.CasesManager

@Configuration
@EnableScheduling
@EnableConfigurationProperties(value = [CasesProperties::class, FacebookProperties::class])
class Configuration {

    @Bean
    @ConditionalOnProperty(value = ["jobs.enabled"], matchIfMissing = false, havingValue = "true")
    fun scheduledJob(casesProperties: CasesProperties, casesService: CasesManager): CasesScheduler {
        return CasesScheduler(casesProperties.cron, casesService)
    }

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()

    @Bean
    fun kotlinModule() = KotlinModule()

    @Bean
    fun afterburnerModule() = AfterburnerModule()
}
