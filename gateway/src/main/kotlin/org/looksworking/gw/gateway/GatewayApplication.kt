package org.looksworking.gw.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@SpringBootApplication
@Controller
class GatewayApplication(private val filterFactory: TokenRelayGatewayFilterFactory) {

    @GetMapping("/")
    fun index(model: Model,
              @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
              @AuthenticationPrincipal oauth2User: OAuth2User): String {
        model.addAttribute("userName", oauth2User.name)
        model.addAttribute("clientName", authorizedClient.clientRegistration.clientName)
        model.addAttribute("userAttributes", oauth2User.attributes)
        return "index"
    }

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
                .route("resource") { r: PredicateSpec ->
                    r.path("/res")
                            .filters { f: GatewayFilterSpec ->
                                f.filters(filterFactory.apply())
                                        .removeRequestHeader("Cookie")
                            } // Prevents cookie being sent downstream
                            .uri("http://gw-oauth-resource:8080/res")
                } // Taking advantage of docker naming
                .build()
    }

//    @Bean
//    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
////        return builder.routes()
////                .route("resource") { r: PredicateSpec ->
////                    r.path("/res")
////                            .filters { f: GatewayFilterSpec -> f.filter(filterFactory.apply()) }
////                            .uri("http://gw-oauth-resource:8080/res")
////                }
////                .build()
//        return builder.routes()
//                .route("resource") { r: PredicateSpec ->
//                    r.path("/res")
//                            .filters { f: GatewayFilterSpec ->
//                                f.filters(filterFactory.apply())
//                                        .removeRequestHeader("Cookie")
//                            } // Prevents cookie being sent downstream
//                            .uri("http://172.10.16.1:8020/res")
//                } // Taking advantage of docker naming
//                .build()
//    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}