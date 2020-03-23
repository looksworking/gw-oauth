package org.looksworking.gw.resource.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class ResourceController {

    @GetMapping("/res")
    fun res(principal: Principal?): String {
        return "The principal is $principal"
    }
}