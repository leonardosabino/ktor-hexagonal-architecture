package hexagonal.template.config.management.health

interface HealthCheck {
    fun healthKey(): String
    fun doHealthCheck(): HealthCheckResult
}

class HealthCheckService(
    private val healthChecks: Set<HealthCheck>
) {
    fun doChecks(): Health {
        val builder = Health.Builder()
        healthChecks.forEach {
            builder.append(it.healthKey(), it.doHealthCheck())
        }
        return builder.build()
    }
}
