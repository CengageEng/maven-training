package com.example.maventraining.ex2.service;

import com.example.maventraining.ex2.util.HelloWorld;
import com.google.common.base.Function;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class GreetingService {
    private HelloWorld helloWorld = new HelloWorld();
    public List<String> greet(Roster roster) {
        return transform(roster.getNames(), new Function<String, String>() {
            public String apply(String name) {
                return helloWorld.hello(name);
            }
        });
    }
}
