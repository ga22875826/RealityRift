package com.teamsix.config;


import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NgrokConfig {
    private static final Logger LOG = LoggerFactory.getLogger(NgrokConfig.class);

    @Bean("ngrokUrl")
    public String ngrokUrl() {
        // consider to use production, dev and test profiles to switch the url string bean
        // e.g. if profile == test, return a fake url string for testing
        // https://www.baeldung.com/spring-boot-junit-5-testing-active-profile
        try {
            URL url = new URL("http://localhost:4040/api/tunnels"); // please consider move this to config/property file
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            StringBuilder result;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
            JsonElement jsonElement = JsonParser.parseString(result.toString());
            return jsonElement.getAsJsonObject()
                    .get("tunnels").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("public_url").getAsString();
        } catch (Exception e) {
            LOG.warn("Ngrok is not running. Falling back to localhost.", e);
            return "http://localhost:8080";
        }
    }
}