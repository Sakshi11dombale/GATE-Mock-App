package com.gate.mocktest.models;

public class TestCardItem {

    public String name;
    public String info;
    public String badge;
    public boolean completed;
    public int completionPct;

    public TestCardItem(
            String n,
            String i,
            String b,
            boolean c,
            int p
    ) {
        name = n;
        info = i;
        badge = b;
        completed = c;
        completionPct = p;
    }
}