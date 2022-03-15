package hexagonal.template.transportLayer.http.dummy

import hexagonal.template.usecase.dummy.OrderDummyUseCase
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import java.util.UUID

fun Route.dummy(injector: DI) {

    val orderDummyUseCase: OrderDummyUseCase = injector.direct.instance()

    post("/v1/dummy") {
        val body = call.parameters
        val dummyId = body["dummyId"] ?: throw IllegalArgumentException("dummyId is a required argument")

        orderDummyUseCase.orderDummy(UUID.fromString(dummyId)).let { result ->
            call.respond(result)
        }
    }
}
