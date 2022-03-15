package hexagonal.template

import hexagonal.template.config.management.env
import hexagonal.template.config.management.health
import hexagonal.template.config.management.info
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

fun Application.management(injector: DI) {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(injector.direct.instance()))
    }

    routing {
        health(injector)
        env(injector)
        info()
    }
}
