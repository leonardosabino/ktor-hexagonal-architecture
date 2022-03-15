package hexagonal.template.datasource.database.dummy

import hexagonal.template.domain.Dummy
import java.util.UUID

interface DummyRepository {
    fun save(dummy: Dummy): Dummy
    fun get(id: UUID): Dummy?
}
