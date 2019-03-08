import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.jvm.tasks.Jar

plugins {
    `kotlin-dsl`
    //id("java-gradle-plugin")
    //kotlin("jvm")

//    id("jacoco")
//    id("com.github.kt3k.coveralls")
    id("com.gradle.plugin-publish") version "0.10.1"
}

gradlePlugin {
    plugins {
        create("markdownlint") {
            id = "com.appmattus.markdown"
            displayName = "markdownlint"
            description = "Linting for markdown files"
            implementationClass = "com.appmattus.markdown.plugin.MarkdownLintPlugin"
        }
    }
}

pluginBundle {
    website = "github"
    vcsUrl = "https://github... .git"
    tags = listOf("markdown", "lint", "format", "style")
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler"))
    implementation(kotlin("script-util"))
    implementation(kotlin("script-runtime"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    api("com.vladsch.flexmark:flexmark-all:0.40.20")

    implementation("com.puppycrawl.tools:checkstyle:8.18")

    testImplementation(kotlin("test"))
    testImplementation(gradleTestKit())
    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.12.1")
    testImplementation("org.mockito:mockito-core:2.24.5")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("com.flextrade.jfixture:jfixture:2.7.2")

    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.1")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.1")
    // spek requires kotlin-reflect, can be omitted if already in the classpath
    testRuntimeOnly(kotlin("reflect"))
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Manifest-Version" to 1.0,
            "Lint-Registry-v2" to "com.appmattus.markdown.MarkdownIssueRegistry"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines = setOf("spek2")
    }

    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.SHORT
    }
}

//tasks.getByName("test").finalizedBy(tasks.getByName("jacocoTestReport"))

tasks.getByName("check").finalizedBy(rootProject.tasks.getByName("detekt"))

/*tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

coveralls {
    sourceDirs = sourceSets.main.get().allSource.srcDirs.map { it.path }
    jacocoReportPath = "$buildDir/reports/jacoco/test/jacocoTestReport.xml"
}

tasks.getByName("jacocoTestReport").finalizedBy(tasks.getByName("coveralls"))

tasks.getByName("coveralls").onlyIf { System.getenv("CI")?.isNotEmpty() == true }*/
