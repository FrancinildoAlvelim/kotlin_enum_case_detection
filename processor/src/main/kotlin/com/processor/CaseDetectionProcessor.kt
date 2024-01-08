package com.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.processor.utils.createExtensionDescription
import com.processor.utils.createExtensionNameByEnumEntry
import java.io.OutputStream

class CaseDetectionProcessor(
    private val codeGenerator: CodeGenerator,
    private val options: Map<String, String>,
    private val logger: KSPLogger
) : SymbolProcessor {
    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }
        resolver.getSymbolsWithAnnotation(CaseDetection::class.qualifiedName.toString())
            .filter {
                it is KSClassDeclaration && it.classKind == ClassKind.ENUM_CLASS
            }.forEach {
                it.accept(CaseDetectionEnumVisitor(), Unit)
            }

        invoked = true
        return emptyList()
    }

    inner class CaseDetectionEnumVisitor : KSVisitorVoid() {

        private fun createEntryDestination(enumClassName: String): (String) -> Unit {
            val destination = initializeFileForExtensions(enumClassName)
            return { entryName: String ->
                val extensionString = createIdentityTestExtensionForEntry(
                    className = enumClassName,
                    entryName = entryName
                )
                destination.write(System.lineSeparator().toByteArray())
                destination.write(extensionString.toByteArray())
            }
        }

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val enumEntries = classDeclaration.declarations
                .filterIsInstance<KSClassDeclaration>()
                .filter {
                    it.classKind == ClassKind.ENUM_ENTRY
                }

            if (enumEntries.any()) {
                val extensionWriter = createEntryDestination(classDeclaration.simpleName.asString())
                enumEntries.forEach {
                    extensionWriter(it.simpleName.asString())
                }
            }
        }

        private fun initializeFileForExtensions(enumClassName: String): OutputStream {
            return codeGenerator.createNewFile(
                dependencies = Dependencies(true),
                packageName = "",
                fileName = enumClassName
            )
        }

        private fun createIdentityTestExtensionForEntry(className: String, entryName: String): String {
            return createExtensionNameByEnumEntry(
                className = className,
                entryName = entryName,
                entryDescription = createExtensionDescription(entryName)
            )
        }
    }

}

class CaseDetectionProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return CaseDetectionProcessor(
            environment.codeGenerator,
            environment.options,
            environment.logger
        )
    }
}