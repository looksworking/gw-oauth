package org.looksworking.gw.auth.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthZServerConfig(private val passwordEncoder: BCryptPasswordEncoder,
                        private val dataSource: DataSource) : AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permit()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        endpoints.authenticationManager(authenticationManager)
        endpoints.tokenStore(tokenStore())
    }
}