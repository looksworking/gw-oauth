import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<Jar> {
    enabled = false
}

tasks.withType<BootJar> {
    enabled = true
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.2.5.RELEASE")
}
