package com.github.alphameo.railways;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.Properties;

import com.github.alphameo.railways.adapters.cli.CliApp;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.application.services.impl.DefaultServiceProvider;
import com.github.alphameo.railways.infrastructure.db.mariadb.MariaDBStorage;

public class App {

    public static String DB_URL_KEY = "DB_URL";
    public static String DB_USER_KEY = "DB_USER";
    public static String DB_PASSWORD_KEY = "DB_PASSWORD";

    public static void main(String[] args) {
        try {
            var url = System.getenv().get(DB_URL_KEY);
            var user = System.getenv().get(DB_USER_KEY);
            var pw = System.getenv().get(DB_PASSWORD_KEY);
            if (url == null) {
                var props = new Properties();
                var envFile = Paths.get(".env");
                try (var inputStream = Files.newInputStream(envFile)) {
                    props.load(inputStream);
                }
                url = props.get(DB_URL_KEY).toString();
                user = props.get(DB_USER_KEY).toString();
                pw = props.get(DB_PASSWORD_KEY).toString();
            }

            final var con = DriverManager.getConnection(url, user, pw);
            MariaDBStorage.setupConnection(con);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        final var storage = MariaDBStorage.getInstance();
        ServiceProvider serviceProvider = new DefaultServiceProvider(storage);

        final var cliApp = new CliApp(serviceProvider);
        cliApp.run();
    }
}
