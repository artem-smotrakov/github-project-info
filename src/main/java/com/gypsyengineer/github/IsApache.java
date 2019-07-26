package com.gypsyengineer.github;

public class IsApache implements DataProvider {

    private final boolean isApache;

    public IsApache(String where) {
        isApache = where.toLowerCase().equals("apache");
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public void get() {
        System.out.printf("Apache? %s%n", isApache ? "yes" : "no");
    }
}
