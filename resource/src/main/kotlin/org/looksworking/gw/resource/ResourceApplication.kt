package org.looksworking.gw.resource

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@SpringBootApplication
class ResourceApplication {

    companion object {
        val LOG = LoggerFactory.getLogger(ResourceApplication::class.java)
    }

    @GetMapping("/res")
    fun resource(jwt: JwtAuthenticationToken, httpRequest: HttpServletRequest): String? {
        LOG.info("***** JWT Headers: {}", jwt.token.headers)
        LOG.info("***** JWT Claims: {}", jwt.token.claims.toString())
        LOG.info("***** JWT Token: {}", jwt.token.tokenValue)
        val logout = """
            <form method="post" action="/exitOAuth" class="inline">
              <button type="submit" name="submit_param" value="submit_value" class="link-button">
                exitOAuth
              </button>
            </form>
        """
        return "$logout Resource accessed by: ${jwt.token.claims["user_name"]} <br> " +
                "with claims: ${jwt.token.claims}"
    }
}

fun main(args: Array<String>) {
    runApplication<ResourceApplication>(*args)
}