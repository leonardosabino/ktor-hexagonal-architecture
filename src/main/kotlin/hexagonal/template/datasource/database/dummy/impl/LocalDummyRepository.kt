package hexagonal.template.datasource.database.dummy.impl

import hexagonal.template.datasource.database.dummy.DummyRepository
import hexagonal.template.domain.Dummy
import mu.KotlinLogging
import java.util.UUID

class LocalDummyRepository : DummyRepository {
    private val logger = KotlinLogging.logger { }

    private val dummies = HashMap<UUID, Dummy>()

    override fun save(dummy: Dummy): Dummy {
        logger.info { "Dummy ${dummy.id} created" }
        dummies[dummy.id] = dummy
        return dummy
    }

    override fun get(id: UUID): Dummy? {
        return dummies[id]
    }
}
