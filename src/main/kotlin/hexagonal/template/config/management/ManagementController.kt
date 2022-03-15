package hexagonal.template.config.management

import com.typesafe.config.Config
import hexagonal.template.config.management.health.HealthCheckService
import hexagonal.template.config.management.health.HealthStatus
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.head
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import java.time.ZoneId

fun Route.health(injector: DI) {
    val managementInjector = DI {
        extend(injector, copy = Copy.All)
    }

    val healthCheckService: HealthCheckService = managementInjector.direct.instance()

    head("/health") {

        val health = healthCheckService.doChecks()
        if (health.status == HealthStatus.UP) {
            call.response.status(HttpStatusCode.OK)
        } else {
            call.response.status(HttpStatusCode.ServiceUnavailable)
        }
    }

    get("/health") {
        val health = healthCheckService.doChecks()
        if (health.status == HealthStatus.UP) {
            call.respond(HttpStatusCode.OK, health)
        } else {
            call.respond(HttpStatusCode.ServiceUnavailable, health)
        }
    }
}

fun Route.env(injector: DI) {
    val config: Config = injector.direct.instance()

    get("/env") {
        val response = config.entrySet()
            .associate { (key, value) -> key to value.unwrapped().toString() }
            .toSortedMap()
        call.respond(response)
    }
}

fun Route.info() {
    get("/info") {
        call.respond(InfoHelper.info)
    }
}

object InfoHelper {
    private val version: String by lazy {
        val pack: Package? = this::class.java.`package`
        pack?.implementationVersion ?: pack?.specificationVersion ?: ""
    }

    private val timezone: String by lazy {
        ZoneId.systemDefault().toString()
    }

    val info: Map<String, String> by lazy {
        mapOf("info.version" to version, "info.timezone" to timezone)
    }
}
