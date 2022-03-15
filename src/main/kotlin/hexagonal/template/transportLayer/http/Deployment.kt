package hexagonal.template.transportLayer.http

import hexagonal.template.transportLayer.http.dummy.dummy
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.JacksonConverter
import io.ktor.routing.routing
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

fun Application.deployment(injector: DI) {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(injector.direct.instance()))
    }

    routing {
        dummy(injector)
    }
}
