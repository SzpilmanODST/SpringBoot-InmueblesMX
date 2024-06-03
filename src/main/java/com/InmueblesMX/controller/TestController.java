package com.InmueblesMX.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/helloAll")
    public String helloAll() {
        return "Hola a todos";
    }

    @GetMapping("/hello-protected")
    public String helloProtected() {
        return "Hola protegido";
    }
}
