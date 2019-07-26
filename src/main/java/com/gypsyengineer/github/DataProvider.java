package com.gypsyengineer.github;

public interface DataProvider {
    boolean isAutomatic();
    void get() throws Exception;
}
