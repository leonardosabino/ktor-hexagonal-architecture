package hexagonal.template.config.management.health

data class Health(
    val status: HealthStatus,
    val results: Map<String, HealthCheckResult> = mapOf()
) {
    data class Builder(
        private val results: MutableMap<String, HealthCheckResult> = mutableMapOf()
    ) {
        fun append(key: String, value: HealthStatus) {
            append(key, HealthCheckResult(value, mapOf()))
        }

        fun append(key: String, value: HealthCheckResult) {
            results[key] = value
        }

        fun build(): Health {
            var healthy = results.isEmpty()
            results.forEach { (_, v) ->
                if (v.status == HealthStatus.UP) {
                    healthy = true
                }
            }

            return Health(status = if (healthy) HealthStatus.UP else HealthStatus.DOWN, results = results.toMap())
        }
    }
}

data class HealthCheckResult(
    val status: HealthStatus,
    val info: Map<String, Any> = mapOf()
)

enum class HealthStatus {
    UP, DOWN
}
