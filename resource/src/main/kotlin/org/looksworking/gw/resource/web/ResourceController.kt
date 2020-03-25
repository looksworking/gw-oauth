//package org.looksworking.gw.resource.web
//
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RestController
//import java.security.Principal
//import javax.servlet.http.HttpServletRequest
//
//@RestController
//class ResourceController {
//
//    @GetMapping("/res")
//    fun res(principal: Principal?, httpServletRequest: HttpServletRequest): String {
//        val headers = httpServletRequest.headerNames
//                .asSequence().map { "$it:${httpServletRequest.getHeader(it)}" }.joinToString("\n")
//        return "The principal is $principal\nheaders:\n$headers"
//    }
//}