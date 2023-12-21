package com.dostavka24.dostavka24.rest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status")
    public String statusCheck() {
        return "Working...";
    }
}
