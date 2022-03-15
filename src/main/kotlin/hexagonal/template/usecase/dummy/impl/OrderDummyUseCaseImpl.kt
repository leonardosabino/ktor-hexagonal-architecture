package hexagonal.template.usecase.dummy.impl

import hexagonal.template.datasource.database.dummy.DummyRepository
import hexagonal.template.domain.Dummy
import hexagonal.template.usecase.dummy.OrderDummyUseCase
import java.util.UUID

class OrderDummyUseCaseImpl(private val dummyRepository: DummyRepository) :
    OrderDummyUseCase {

    override fun orderDummy(dummyId: UUID): Dummy {
        val newDummy = Dummy(dummyId = dummyId)
        return dummyRepository.save(newDummy)
    }
}
