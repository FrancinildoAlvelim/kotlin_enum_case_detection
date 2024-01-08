package com.processor.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UtilsKtTest {

    @Test
    fun testCreateExtensionDescriptionWithPascalCase() {
        val entryName = "TEST_CASE"
        val description = createExtensionDescription(entryName)
        assertTrue(description.matches(PASCAL_CASE_REGEX.toRegex()))
    }

    @Test
    fun testIfGeneratedStringExtensionIncludesDescriptionWithConventionPrefix() {
        val className = "Enum"
        val entryName = "TEST"
        val description = createExtensionDescription(entryName)
        val generatedExtension = createExtensionNameByEnumEntry(
            className = className,
            entryName = entryName,
            entryDescription = description
        )
        assertTrue(generatedExtension.contains(BOOLEAN_EXPRESSION_CONVENTION_PREFIX.plus(description)))
    }

    companion object {
        const val PASCAL_CASE_REGEX = "^[A-Z][a-z]*([A-Z][a-z]*)*\$"
    }
}