package org.looksworking.gw.auth.conf

import com.nimbusds.jose.jwk.JWKSet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


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
    fun user(): Map<String, Any> {
        val name = SecurityContextHolder.getContext().authentication.name
        logger.info("/userinfo called authenticating [$name]")
        return mapOf("user_name" to name)
    }
}