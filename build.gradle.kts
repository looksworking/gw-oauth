buildscript {
    val bootVersion = "2.2.5.RELEASE"
    val kotlinVersion = "1.3.70"

    project.extra.set("bootVersion", bootVersion)
    project.extra.set("kotlinVersion", kotlinVersion)
    
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$bootVersion")
   }
}

plugins {
    val bootVersion = "2.2.5.RELEASE"
    val kotlinVersion = "1.3.70"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.springframework.boot") version bootVersion
}

allprojects {
    group = "org.looksworking"
    version = "0.1.0"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply {
        plugin("idea")
        plugin("java")
        plugin("jacoco")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }

    configure<JavaPluginConvention> {
        setSourceCompatibility(1.8)
        setTargetCompatibility(1.8)
        sourceSets.getByName("main").java.srcDirs("${project.buildDir}/generated/source/kaptKotlin/main/")
    }

    dependencies {
        compile(kotlin("stdlib"))
        compile(kotlin("reflect"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
//        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

println("GwAuthVersion: ${project.version}")
