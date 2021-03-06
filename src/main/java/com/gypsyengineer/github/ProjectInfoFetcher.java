package com.gypsyengineer.github;

import org.apache.commons.cli.*;
import org.kohsuke.github.GitHub;

import java.net.URL;

public class ProjectInfoFetcher {

    private static final String usage =
            "java -jar github-project-info-1.0-SNAPSHOT-all.jar [options]";

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

        CommandLine commandLine;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            System.out.println(e);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, options);
            return;
        }

        if (commandLine.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, options);
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
                new NumberOfStars(where, name, github),
                new NumberOfWatchers(where, name, github),
                new IsApache(where),
                new IsEclipse(where),
                new HasSecurityTeam(where),
        };

        System.out.printf("Let's fetch data about %s%n", url);
        for (DataProvider provider : providers) {
            provider.get();
        }
    }
}
