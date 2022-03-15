package hexagonal.template.architecture

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameContaining
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures
import io.kotest.core.spec.style.FunSpec

class ArchitectureTest : FunSpec({

    test("Architecture test") {
        val importedClasses: JavaClasses = ClassFileImporter().importPackages("hexagonal.template")

        val layersValidator: ArchRule = Architectures.layeredArchitecture()
            .layer("TransportLayer").definedBy("..transportLayer..")
            .layer("UseCase").definedBy("..usecase..")
            .layer("Config").definedBy("..config..")
            .layer("Datasource").definedBy("..datasource..")
            .whereLayer("TransportLayer").mayOnlyBeAccessedByLayers("Config")
            .whereLayer("UseCase").mayOnlyBeAccessedByLayers("TransportLayer", "Config", "Datasource")
            .whereLayer("Datasource").mayOnlyBeAccessedByLayers("UseCase", "Config")
            .ignoreDependency(nameContaining("Application"), alwaysTrue())

        layersValidator.check(importedClasses)
    }
})
