import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset

apply {
    plugin("com.squareup.sqldelight")
}

plugins {
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
}
apply(from = File("sqldelight.gradle"))

repositories {
    maven ( "https://dl.bintray.com/kotlin/kotlinx" )
    maven ( "https://dl.bintray.com/kotlin/ktor" )
    maven ( "https://dl.bintray.com/soywiz/soywiz" )
    mavenCentral()
    jcenter()
    maven ( "https://jitpack.io" )
}

val kotlinVersion = "1.3.31"
val ktor_version = "1.1.5"
val sqldelight_version = "1.1.3"
val serialization_version = "0.11.0"
val coroutines_version = "1.2.1"
val klock_version = "1.4.0"

kotlin {
    jvm("android")
    iosArm64()
    iosX64()

    data class IosTarget(val name: String, val preset: String, val id: String)

    val iosTargets = listOf(
        IosTarget("ios", "iosArm64", "ios-arm64"),
        IosTarget("iosSim", "iosX64", "ios-x64")
    )

    for ((targetName, presetName, id) in iosTargets) {
        targetFromPreset(presets.getByName<KotlinNativeTargetPreset>(presetName), targetName) {
            compilations {
                getByName("main") {
                    binaries {
                        framework("shared")
                    }
                }
            }
        }
    }

    println(targets.names)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-auth:$ktor_version")
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("com.soywiz:klock-metadata:$klock_version")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
                implementation("com.squareup.sqldelight:runtime-jvm:$sqldelight_version")
                implementation("io.ktor:ktor-client-android:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
                implementation("com.soywiz:klock-android:$klock_version")
                implementation("com.squareup.sqldelight:android-driver:$sqldelight_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
                api("io.ktor:ktor-client-mock-jvm:$ktor_version")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutines_version")
                implementation("com.squareup.sqldelight:ios-driver:$sqldelight_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialization_version")
                implementation("io.ktor:ktor-client-ios:$ktor_version")
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios-iosarm64:$ktor_version")
                implementation("com.soywiz:klock-iosarm64:$klock_version")
            }
        }

        val iosX64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios-iosx64:$ktor_version")
                implementation("com.soywiz:klock-iosx64:$klock_version")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            }
        }

        val iosTest by getting {
            dependencies {
                api("io.ktor:ktor-client-mock-native:$ktor_version")
            }
        }
    }
}

configurations.create("compileClasspath")

apply(from = File("xcode.gradle"))

// tasks.build.dependsOn("packForXCode") // .dependsOn packForXCode

/*
tasks.register("packForXCode") {
    val frameworkDir = File(buildDir, "xcode-frameworks")
    val mode = project.findProperty("XCODE_CONFIGURATION")?.toString()?.toUpperCase() ?: "DEBUG"

    inputs.property("mode") = mode // "mode", mode
    dependsOn kotlin.targets.ios.compilations.main.linkTaskName("FRAMEWORK", mode)

    from { kotlin.targets.ios.compilations.main.getBinary("FRAMEWORK", mode).parentFile }
    into frameworkDir

            doLast {
                new File(frameworkDir, 'gradlew').with {
                text = "#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n"
                setExecutable(true)
            }
            }
}



task packForXCode(type: Sync) {
    final File frameworkDir = new File(buildDir, "xcode-frameworks")
    final String mode = project.findProperty("XCODE_CONFIGURATION")?.toUpperCase() ?: 'DEBUG'

    inputs.property "mode", mode
    dependsOn kotlin.targets.ios.compilations.main.linkTaskName("FRAMEWORK", mode)

    from { kotlin.targets.ios.compilations.main.getBinary("FRAMEWORK", mode).parentFile }
    into frameworkDir

    doLast {
        new File(frameworkDir, 'gradlew').with {
            text = "#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n"
            setExecutable(true)
        }
    }
}

tasks.build.dependsOn packForXCode

task iosTest {
    def device = project.findProperty("iosDevice")?.toString() ?: "iPhone 8"
    dependsOn 'linkTestDebugExecutableIos'
    group = JavaBasePlugin.VERIFICATION_GROUP
    description = "Runs tests for target 'ios' on an iOS simulator"

    doLast {
        def binary = kotlin.targets.ios.binaries.getExecutable('test', 'DEBUG').outputFile
        exec {
            commandLine 'xcrun', 'simctl', 'spawn', device, binary.absolutePath
        }
    }
}
*/