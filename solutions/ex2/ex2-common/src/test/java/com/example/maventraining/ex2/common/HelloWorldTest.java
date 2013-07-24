package com.example.maventraining.ex2.common;

import com.example.maventraining.ex2.common.HelloWorld;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest {
    @Test
    public void testSomething() {
        HelloWorld helloWorld = new HelloWorld();
        assertEquals("Hello, Dave!", helloWorld.hello("Dave"));
    }
}
