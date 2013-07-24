package com.example.maventraining.ex3.service;

import java.util.List;

public class Roster {
    private List<String> names;

    public Roster(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }
}
