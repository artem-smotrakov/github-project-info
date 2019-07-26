package com.gypsyengineer.github;

import org.kohsuke.github.GitHub;

public abstract class AbstractDataProvider implements DataProvider {

    protected final String where;
    protected final String name;
    protected final GitHub github;

    public AbstractDataProvider(String where, String name, GitHub github) {
        this.where = where;
        this.name = name;
        this.github = github;
    }
}
