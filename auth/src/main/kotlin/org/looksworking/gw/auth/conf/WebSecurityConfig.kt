package org.looksworking.gw.auth.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@Order(1)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {


        http.authorizeRequests()
                .antMatchers("/login**", "/oauth/**", "/userinfo**", "/token_keys**")
                .permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .logout().logoutUrl("/exitOAuth")
                .clearAuthentication(true)
                .deleteCookies("SESSION")
                .logoutSuccessUrl("http://172.10.16.1:8030/res")
                .and().csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder().encode("pass1"))
                .roles("USER")
                .and()
                .withUser("user2")
                .password(passwordEncoder().encode("pass1"))
                .roles("USER")
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
