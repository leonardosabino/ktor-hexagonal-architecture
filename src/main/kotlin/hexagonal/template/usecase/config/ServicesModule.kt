package hexagonal.template.usecase.config

import hexagonal.template.datasource.config.repositories
import hexagonal.template.usecase.dummy.impl.OrderDummyUseCaseImpl
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@KtorExperimentalAPI
val services = DI.Module("services") {
    importOnce(repositories)

    bind() from singleton { OrderDummyUseCaseImpl(instance()) }
}
