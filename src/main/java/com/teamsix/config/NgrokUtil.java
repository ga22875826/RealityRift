package com.teamsix.config;



import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
public class NgrokUtil {

	public String getNgrokUrl() {
	    try {
	        URL url = new URL("http://localhost:4040/api/tunnels");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder result = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            result.append(line);
	        }
	        reader.close();

	        JsonElement jsonElement = JsonParser.parseString(result.toString());
	        String ngrokUrl = jsonElement.getAsJsonObject()
	                            .get("tunnels").getAsJsonArray()
	                            .get(0).getAsJsonObject()
	                            .get("public_url").getAsString();

	        return ngrokUrl;
	    } catch (Exception e) {
	        System.out.println("Ngrok is not running. Falling back to localhost.");
	        return "http://localhost:8080";
	    }
	}
}