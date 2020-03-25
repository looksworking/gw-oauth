package org.looksworking.gw.resource

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@RestController
@SpringBootApplication
class ResourceApplication {

    companion object {
        val LOG = LoggerFactory.getLogger(ResourceApplication::class.java)
    }

    @GetMapping("/res")
    fun resource(@AuthenticationPrincipal jwt: Jwt, httpRequest: HttpServletRequest): String? {
        LOG.trace("***** JWT Headers: {}", jwt.headers)
        LOG.trace("***** JWT Claims: {}", jwt.claims.toString())
        LOG.trace("***** JWT Token: {}", jwt.tokenValue)
        val part1 = String.format("Resource accessed by: %s (with subjectId: %s)",
                jwt.claims["user_name"],
                jwt.subject)
        val headers = Collections.list(httpRequest.headerNames).stream()
                .map { headerName: String ->
                    "$headerName:" + httpRequest.getHeader("""
    $headerName
    
    """.trimIndent())
                }
                .collect(Collectors.joining("\n"))
        return """
            $part1
            $headers
            """.trimIndent()
    }
}

fun main(args: Array<String>) {
    runApplication<ResourceApplication>(*args)
}