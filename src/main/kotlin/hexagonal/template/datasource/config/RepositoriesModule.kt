package hexagonal.template.datasource.config

import hexagonal.template.datasource.database.dummy.impl.LocalDummyRepository
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

@KtorExperimentalAPI
val repositories = DI.Module("repositories") {

    bind { singleton { LocalDummyRepository() } }
}
