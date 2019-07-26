package com.gypsyengineer.github;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Date;

public class NumberOfCommits extends AbstractDataProvider {

    private final long days;

    public NumberOfCommits(String where, String name, GitHub github, long days) {
        super(where, name, github);
        this.days = days;
    }

    @Override
    public boolean isAutomatic() {
        return true;
    }

    @Override
    public void get() throws IOException {
        GHRepository repository = github.getRepository(String.format("%s/%s", where, name));
        int counter = 0;
        long delta = days * 24 * 60 * 60 * 1000;
        Date date = new Date(System.currentTimeMillis() - delta);
        for (GHCommit commit : repository.listCommits()) {
            if (commit.getCommitDate().after(date)) {
                counter++;
            } else {
                break;
            }
        }
        System.out.printf("commits in last %d days: %d%n", days, counter);
    }
}
