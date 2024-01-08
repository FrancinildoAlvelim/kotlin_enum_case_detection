package com.processor.utils

import com.processor.extensions.capitalized

internal const val BOOLEAN_EXPRESSION_CONVENTION_PREFIX = "is"
internal const val UNDERLINE_SEPARATOR_REGEX = "^[a-zA-Z]+(_[a-zA-Z]+)*$"
internal const val SNAKE_CASE_DELIMITER = "_"
internal const val EMPTY_JOIN_SEPARATOR = ""

internal fun createExtensionDescription(entryName: String): String {
    val isSnakeCased = entryName.matches(UNDERLINE_SEPARATOR_REGEX.toRegex())
    val items = buildList {
        if (isSnakeCased) {
            val entries = entryName.split(SNAKE_CASE_DELIMITER)
            entries.forEach {
                add(it.capitalized())
            }
        } else {
            add(entryName.capitalized())
        }
    }
    return items.joinToString(EMPTY_JOIN_SEPARATOR)
}

internal fun createExtensionNameByEnumEntry(className: String, entryName: String, entryDescription: String): String {
    val extensionName = "${className}.${BOOLEAN_EXPRESSION_CONVENTION_PREFIX}${entryDescription}"
    return "fun $extensionName() = this == ${className}.$entryName"
}