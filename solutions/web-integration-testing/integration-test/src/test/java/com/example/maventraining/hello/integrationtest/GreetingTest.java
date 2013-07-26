package com.example.maventraining.hello.integrationtest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GreetingTest {
    @Test
    public void showsGreeting() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(new HttpGet("http://localhost:8080/George"));
        assertEquals("Hello, George!\n", EntityUtils.toString(response.getEntity(), "UTF-8"));
    }
}
