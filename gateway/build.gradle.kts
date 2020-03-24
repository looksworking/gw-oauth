import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<Jar> {
    enabled = false
}

tasks.withType<BootJar> {
    enabled = true
}

dependencies {
    compile("org.springframework.cloud:spring-cloud-starter-gateway:2.2.2.RELEASE")
    compile("org.springframework.cloud:spring-cloud-starter-security:2.2.1.RELEASE")
    compile("org.springframework.boot:spring-boot-starter-oauth2-client:2.2.5.RELEASE")
    compile("org.springframework.security:spring-security-jwt:1.1.0.RELEASE")
}
