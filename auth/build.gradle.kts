import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<Jar> {
    enabled = false
}

tasks.withType<BootJar> {
    enabled = true
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.security:spring-security-oauth2-jose:5.3.0.RELEASE")
    compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.2.5.RELEASE")
    compile("org.postgresql:postgresql:42.2.11")
    compile("org.springframework.data:spring-data-commons")
    compile("org.springframework.boot:spring-boot-starter-jdbc")
    compile("org.liquibase:liquibase-core:3.8.8")
    compile("com.nimbusds:nimbus-jose-jwt:8.10")
}
