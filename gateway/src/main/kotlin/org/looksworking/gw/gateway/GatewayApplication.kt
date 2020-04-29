package org.looksworking.gw.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory
import org.springframework.context.annotation.Bean


@SpringBootApplication
class GatewayApplication(private val filterFactory: TokenRelayGatewayFilterFactory) {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
                .route("resource") { r: PredicateSpec ->
                    r.path("/res")
                            .filters { f: GatewayFilterSpec ->
                                f.filters(filterFactory.apply())
                                        .removeRequestHeader("Cookie")
                            }
                            .uri("http://gw-oauth-resource:8080/res")
                }
                .route("exitOAuth") { r: PredicateSpec ->
                    r.path("/exitOAuth")
                            .filters { f: GatewayFilterSpec ->
                                f.filters(filterFactory.apply())
                            }
                            .uri("http://gw-oauth-auth:8080/exitOAuth")
                }
                .build()
    }

}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}