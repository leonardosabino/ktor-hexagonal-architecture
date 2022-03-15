package hexagonal.template.usecase.dummy

import hexagonal.template.domain.Dummy
import java.util.UUID

interface OrderDummyUseCase {

    fun orderDummy(dummyId: UUID): Dummy
}
