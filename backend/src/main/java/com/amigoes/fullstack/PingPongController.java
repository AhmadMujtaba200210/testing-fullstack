package com.amigoes.fullstack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    record PingPong(String result){}

    @GetMapping("/ping")
    public PingPong getPingPong(String result){
        return new PingPong("Pong");
    }
}
