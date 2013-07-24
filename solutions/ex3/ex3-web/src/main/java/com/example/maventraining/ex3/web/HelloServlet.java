package com.example.maventraining.ex3.web;

import com.example.maventraining.ex3.util.HelloWorld;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private HelloWorld helloWorld = new HelloWorld();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String name = pathInfo.substring(1, pathInfo.length());
        res.getWriter().println(helloWorld.hello(name));
    }
}
