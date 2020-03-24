package org.looksworking.gw.auth.conf

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore
import java.security.interfaces.RSAPublicKey
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthZServerConfig(private val passwordEncoder: BCryptPasswordEncoder,
                        private val dataSource: DataSource,
                        @Qualifier("authenticationManagerBean") private val authenticationManager: AuthenticationManager) : AuthorizationServerConfigurerAdapter() {

    private val keyPair = KeyStoreKeyFactory(ClassPathResource("keyPair.jks"), "Welcome1".toCharArray()).getKeyPair("jwt")

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permit()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    @Bean
    fun tokenStore(): TokenStore {
//        return JdbcTokenStore(dataSource)
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setKeyPair(keyPair)
//        converter.setSigningKey("123")
        return converter
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        endpoints.authenticationManager(authenticationManager)
//        endpoints.tokenStore(tokenStore())
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }

    @Bean
    fun jwkSet(): JWKSet {
        val builder: RSAKey.Builder = RSAKey.Builder(keyPair.getPublic() as RSAPublicKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
        return JWKSet(builder.build())
    }
}