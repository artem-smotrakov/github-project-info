package com.gypsyengineer.github;

public class IsEclipse implements DataProvider {

    private final boolean isEclipse;

    public IsEclipse(String where) {
        isEclipse = where.toLowerCase().equals("eclipse");
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public void get() {
        System.out.printf("Eclipse? %s%n", isEclipse ? "yes" : "no");
    }
}
