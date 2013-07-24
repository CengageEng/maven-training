package com.example.maventraining.ex2.service;

import com.example.maventraining.ex2.service.GreetingService;
import com.example.maventraining.ex2.service.Roster;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class GreetingServiceTest {
    @Test
    public void greetsEveryoneInRoster() {
        Roster roster = new Roster(Arrays.asList("Joe", "Bob", "Joe-Bob", "Bobby", "Joey", "Bobby-Joey", "Worthington"));
        GreetingService service = new GreetingService();

        assertEquals(Arrays.asList(
                "Hello, Joe!",
                "Hello, Bob!",
                "Hello, Joe-Bob!",
                "Hello, Bobby!",
                "Hello, Joey!",
                "Hello, Bobby-Joey!",
                "Hello, Worthington!"
        ), service.greet(roster));
    }
}
