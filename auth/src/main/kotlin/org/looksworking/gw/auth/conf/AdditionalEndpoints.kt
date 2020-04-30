package org.looksworking.gw.auth.conf

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jose.jwk.JWKSet
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.jwt.JwtHelper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
class AdditionalEndpoints(private val jwkSet: JWKSet, private val objectMapper: ObjectMapper) {

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

        val token = httpServletRequest.getHeader("Authorization").substringAfter("Bearer ")
        val claims = JwtHelper.decode(token).claims
        val map = objectMapper.readValue(claims, Map::class.java)

        logger.info("/userinfo called authenticating: \n$headers")
        logger.info("SecurityContext: ${SecurityContextHolder.getContext().authentication.isAuthenticated}")
        logger.info("Principal name: ${map["user_name"]}")
        return mapOf("sub" to map["user_name"].toString())
    }
}