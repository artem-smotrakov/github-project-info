package com.gypsyengineer.github;

import org.apache.commons.cli.*;
import org.kohsuke.github.GitHub;

import java.net.URL;

public class ProjectInfoFetcher {

    public static void main(String... args) throws Exception {
        Options options = new Options();
        options.addOption( "h", "help", false,
                "print this message" );
        options.addOption( "a", "only-automatic", false,
                "gather only data which can be gathered automatically" );
        options.addOption( "m", "only-manual", false,
                "gather only data which requires user's input" );
        options.addOption(Option.builder("u")
                .required()
                .hasArg()
                .longOpt("url")
                .desc("repository URL")
                .build());
        options.addOption(Option.builder("t")
                .longOpt("token")
                .hasArg()
                .required()
                .desc("access token")
                .build());

        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(ProjectInfoFetcher.class.getCanonicalName(), options);
            return;
        }

        if (commandLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(ProjectInfoFetcher.class.getCanonicalName(), options);
            return;
        }

        String token = commandLine.getOptionValue("token");
        GitHub github = GitHub.connectUsingOAuth(token);

        URL url = new URL(commandLine.getOptionValue("url"));
        String[] parts = url.getPath().split("/");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "What the hell! The URL doesn't seem to be correct!");
        }

        String where = parts[1];
        String name = parts[2];

        DataProvider[] providers = {
                new NumberOfCommits(where, name, github, 90),
        };

        System.out.printf("Fetch data about %s%n", url);
        for (DataProvider provider : providers) {
            provider.get();
        }
    }
}
