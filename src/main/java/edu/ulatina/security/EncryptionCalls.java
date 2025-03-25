package edu.ulatina.security;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import com.fasterxml.jackson.databind.*;

public class EncryptionCalls {
    private HttpClient client = HttpClient.newHttpClient();

    private HttpRequest request;

    public String encrypt(String text) {

        String textResponse = null;

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/encryp?text="+ text))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            textResponse = rootNode.path("text").asText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return textResponse;
    }

    public String decrypt(String text) {

        String textResponse = null;

        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/dencryp?text="+ text))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            textResponse = rootNode.path("text").asText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return textResponse;
    }
    
}
