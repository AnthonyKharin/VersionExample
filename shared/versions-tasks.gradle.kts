import com.example.getAppVersionString
import com.example.getNumericAppVersion

tasks.named("preBuild") {
    dependsOn("generateXcodeVersionConfig")
    dependsOn("generateVersionConfig")
}

tasks.matching { it.name.startsWith("compileKotlinIos") }.configureEach {
    dependsOn("generateXcodeVersionConfig")
    dependsOn("generateVersionConfig")
}

tasks.register("generateVersionConfig") {
    val configFile =
        file(project.rootDir.toString() + "/shared/src/commonMain/kotlin/com/example/VersionConfig.kt")
    outputs.file(configFile)
    val content = """
            // Не менять файл, он сгенерирован с помощью таски generateVersionConfig
            object VersionConfig {
                const val VERSION_CODE = ${getNumericAppVersion()}
                const val VERSION_NAME = "${getAppVersionString()}"
            }

    """.trimIndent()

    outputs.upToDateWhen {
        configFile.takeIf { it.exists() }?.readText() == content
    }
    doLast {
        configFile.writeText(content)
    }
}

tasks.register("generateXcodeVersionConfig") {
    val configFile =
        file(project.rootDir.toString() + "/iosApp/Versions.xcconfig")
    outputs.file(configFile)
    val content = """
        BUNDLE_VERSION=${getNumericAppVersion()}
        BUNDLE_SHORT_VERSION_STRING=${getAppVersionString()}
    """.trimIndent()

    outputs.upToDateWhen {
        configFile.takeIf { it.exists() }?.readText() == content
    }
    doLast {
        configFile.writeText(content)
    }
}
