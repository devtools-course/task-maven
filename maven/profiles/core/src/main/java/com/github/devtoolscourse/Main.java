package com.github.devtoolscourse;

import java.io.IOException;
import java.util.Properties;

public class Main {
    static void main() throws IOException {
        System.out.println(Provider.VALUE);
        try (var is = Main.class.getClassLoader().getResourceAsStream("db.properties")) {
            var props = new Properties();
            props.load(is);

            System.out.println(props.getProperty("db.url"));
        }
    }
}
