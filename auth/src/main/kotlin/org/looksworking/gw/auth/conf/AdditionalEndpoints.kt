package org.looksworking.gw.auth.conf

import com.nimbusds.jose.jwk.JWKSet
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest
import javax.servlet.http.HttpServletRequest


@RestController
class AdditionalEndpoints(private val jwkSet: JWKSet) {

    companion object {
        private val logger = LoggerFactory.getLogger(AdditionalEndpoints::class.java)
    }

    @GetMapping("/token_keys")
    fun keys(): Map<String, Any> {
        return jwkSet.toJSONObject()
    }

    @GetMapping("/userinfo")
    fun user(httpServletRequest: HttpServletRequest): Map<String, Any> {

        val headers = httpServletRequest.headerNames
                .asSequence().map { "$it:${httpServletRequest.getHeader(it)}" }.joinToString("\n")

        logger.info("/userinfo called authenticating: \n$headers")
        logger.info("SecurityContext: ${SecurityContextHolder.getContext().authentication.isAuthenticated}")
        return mapOf("sub" to "auth")
    }
}