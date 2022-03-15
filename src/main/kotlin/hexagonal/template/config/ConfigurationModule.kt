package hexagonal.template.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import hexagonal.template.config.management.health
import hexagonal.template.usecase.config.services
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import mu.KotlinLogging
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val logger = KotlinLogging.logger { }

val serialization = DI.Module("serialization") {
    bind() from singleton { objectMapper() }
}

fun objectMapper(): ObjectMapper {
    return JacksonExtension.jacksonObjectMapper
}

@KtorExperimentalAPI
val configuration = DI.Module("configuration") {
    val profileActive = System.getenv("KTOR_PROFILE") ?: "local"
    logger.info { "Current profile: $profileActive" }

    val profileConfig = ConfigFactory.parseResources("application-$profileActive.conf")
    val defaultConfig = ConfigFactory.parseResources("application.conf")

    bind<Config>() with singleton {
        val conf = ConfigFactory.load(profileConfig)
            .withFallback(defaultConfig)
            .resolve()
        conf
    }
    bind<ApplicationConfig>() with singleton { HoconApplicationConfig(instance()) }
}

@KtorExperimentalAPI
val injector = DI {
    import(configuration)
    import(serialization)
    import(services)
    import(health)
}

@KtorExperimentalAPI
fun ApplicationConfig.getString(fieldName: String): String? {
    return propertyOrNull(fieldName)?.getString()
}

@KtorExperimentalAPI
fun ApplicationConfig.getInt(fieldName: String): Int? {
    return getString(fieldName)?.toInt()
}

@KtorExperimentalAPI
fun ApplicationConfig.getLong(fieldName: String): Long? {
    return getString(fieldName)?.toLong()
}

@KtorExperimentalAPI
fun ApplicationConfig.getBoolean(fieldName: String): Boolean? {
    return getString(fieldName)?.toBoolean()
}
