package hexagonal.template.config.management

import hexagonal.template.config.management.health.HealthCheck
import hexagonal.template.config.management.health.HealthCheckService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.setBinding
import org.kodein.di.singleton

val health = DI.Module("health") {
    bind() from setBinding<HealthCheck>()

    bind { singleton { HealthCheckService(instance()) } }
}
