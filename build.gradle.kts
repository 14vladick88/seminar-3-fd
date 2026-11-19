import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm") version "2.4.21"
    application
}

repositories { mavenCentral() }

kotlin { jvmToolchain(26) }

application { mainClass.set("MainKt") }

dependencies {
    implementation(project(":task01"))
    implementation(project(":task02"))
    implementation(project(":task03"))
    implementation(project(":task04"))
    implementation(project(":task05"))
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories { mavenCentral() }
    extensions.configure<KotlinJvmProjectExtension> { jvmToolchain(26) }
    dependencies {
        "testImplementation"(kotlin("test-junit5"))
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = false
        }
    }
}
