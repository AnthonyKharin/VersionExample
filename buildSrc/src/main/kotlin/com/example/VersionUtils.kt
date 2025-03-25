package com.example

import org.gradle.api.Project

fun Project.getAppVersionString(): String {
    val (major, minor, patch) = getSanitizedVersionParts()
    return "$major.$minor.$patch"
}

fun Project.getNumericAppVersion(): Int {
    val (major, minor, patch) = getSanitizedVersionParts()
    return major * 1_000_000 + minor * 1_000 + patch
}

private fun Project.getSanitizedVersionParts(): Triple<Int, Int, Int> {
    val sanitizedVersionString = project.version.toString().let { appVersionString ->
        appVersionString.indexOfOrNull("-")?.let { indexOfFirstDash ->
            appVersionString.substring(0, indexOfFirstDash)
        } ?: appVersionString
    }

    return sanitizedVersionString
        .split('.')
        .take(3)
        .map { it.toInt() }
        .let { Triple(it[0], it[1], it[2]) }
}

private fun String.indexOfOrNull(substring: String): Int? {
    val index = indexOf(substring)
    return if (index >= 0) index else null
}
