package hexagonal.template

import hexagonal.template.config.injector
import hexagonal.template.transportLayer.http.deployment
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import mu.KotlinLogging
import org.kodein.di.direct
import org.kodein.di.instance

@KtorExperimentalAPI
fun main() {
    val logger = KotlinLogging.logger { }
    logger.info { "Starting the ktor-hexagonal-template!" }

    var management: ApplicationEngine? = null
    var deployment: ApplicationEngine? = null

    try {

        management = embeddedServer(
            factory = Netty,
            environment = applicationEngineEnvironment {
                log = logger
                config = injector.direct.instance()

                module { management(injector) }

                connector {
                    host = "0.0.0.0"
                    port = config.property("ktor.management.port").getString().toInt()
                }
            }
        ).start(wait = false)

        deployment = embeddedServer(
            factory = Netty,
            environment = applicationEngineEnvironment {
                log = logger
                config = injector.direct.instance()

                module { deployment(injector) }

                connector {
                    host = "0.0.0.0"
                    port = config.property("ktor.deployment.port").getString().toInt()
                }
            }
        ).start(wait = true)
    } catch (t: Throwable) {
        logger.error { t.toString() }
        throw t
    } finally {
        management?.stop(0, 10000)
        deployment?.stop(0, 10000)
    }

    logger.info { "ktor-hexagonal-template application ended!" }
}
