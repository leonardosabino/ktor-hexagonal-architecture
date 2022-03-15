package hexagonal.template.domain

import java.util.UUID

data class Dummy(
    val id: UUID = UUID.randomUUID(),
    val dummyId: UUID? = null
) {
    fun validate(): Boolean {
        return true
    }
}
