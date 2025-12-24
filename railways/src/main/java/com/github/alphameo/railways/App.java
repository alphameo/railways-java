package com.github.alphameo.railways;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.Properties;

import com.github.alphameo.railways.adapters.cli.CliApp;
import com.github.alphameo.railways.adapters.web.WebAppInitializer;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.application.services.impl.DefaultServiceProvider;
import com.github.alphameo.railways.infrastructure.db.mariadb.MariaDBStorage;

public class App {

    public static String DB_URL_KEY = "DB_URL";
    public static String DB_USER_KEY = "DB_USER";
    public static String DB_PASSWORD_KEY = "DB_PASSWORD";
    public static String PORT = "PORT";

    public static void main(String[] args) {
        String url, user, pw, port;
        url = System.getenv().get(DB_URL_KEY);
        user = System.getenv().get(DB_USER_KEY);
        pw = System.getenv().get(DB_PASSWORD_KEY);
        port = System.getenv().get(PORT);
        if (url == null) {
            try {
                var props = new Properties();
                var envFile = Paths.get(".env");
                try (var inputStream = Files.newInputStream(envFile)) {
                    props.load(inputStream);
                }
                url = props.get(DB_URL_KEY).toString();
                user = props.get(DB_USER_KEY).toString();
                pw = props.get(DB_PASSWORD_KEY).toString();
                port = props.get(PORT).toString();
            } catch (Exception e) {
                System.out.printf("Error while reading .env file: %s\n", e.getMessage());
                System.exit(1);
            }
        }

        try {

            final var con = DriverManager.getConnection(url, user, pw);
            MariaDBStorage.setupConnection(con);
        } catch (Exception e) {
            System.out.printf("Failed to setup connection to database: %s\n", e.toString());
        }

        final var storage = MariaDBStorage.getInstance();
        ServiceProvider serviceProvider = new DefaultServiceProvider(storage);

        if (args.length >= 2) {
            port = args[1];
        }
        if (args.length >= 1) {
            switch (args[0]) {
                case "web":
                    System.out.printf("Starting web server on port %s...\n", port);
                    WebAppInitializer webApp = new WebAppInitializer(serviceProvider, Integer.parseInt(port));
                    try {
                        webApp.start();
                    } catch (Exception e) {
                        System.err.printf("Failed to start web server: %s\n", e.getMessage());
                        System.exit(1);
                    }
                    break;
                case "cli":
                    final var cliApp = new CliApp(serviceProvider);
                    cliApp.run();
            }
        } else {
            final var cliApp = new CliApp(serviceProvider);
            cliApp.run();
        }
    }
}
