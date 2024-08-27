package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MainTest {
    public static void main(String[] args) throws IOException {
        Properties profile = new Properties();

        System.out.println(Files.exists(Paths.get("src/main/resources/hiber.properties")));
        System.out.println(Files.exists(Paths.get("src/main/resources/jdbc.properties")));
    }

}
