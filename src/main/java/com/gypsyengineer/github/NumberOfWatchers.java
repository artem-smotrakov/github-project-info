package com.gypsyengineer.github;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class NumberOfWatchers extends AbstractDataProvider {

    public NumberOfWatchers(String where, String name, GitHub github) {
        super(where, name, github);
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public void get() throws IOException {
        GHRepository repository = github.getRepository(String.format("%s/%s", where, name));
        System.out.printf("Watchers: %d%n", repository.getSubscribersCount());
    }
}
