package com.tian.rabbitmq.demo9_spring1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqController {

    @Autowired
    Sender sender;

    @RequestMapping("/rabbitmq")
    public String test() {
        try {
            sender.send("key1", "Hello World!");
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

}