package com.gypsyengineer.github;

public class HasSecurityTeam implements DataProvider {

    private final boolean hasSecurityTeam;

    public HasSecurityTeam(String where) {
        String org = where.toLowerCase();
        hasSecurityTeam = org.equals("apache") || org.equals("spring-projects");
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public void get() {
        System.out.printf("Has a security team? %s%n", hasSecurityTeam ? "yes" : "no");
    }
}
