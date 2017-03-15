package com.example;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author roland
 * @since 15/03/2017
 */
@RestController
public class RandomNumberEndpoint {

    private static Random random = new Random();

    @RequestMapping(value = "/random", produces = "application/json")
    public Map getRandomNumber() {
        return Collections.singletonMap("random", random.nextInt());
    }
}
