package com.example.apim;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.sound.midi.Patch;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
//import org.apache.commons.io.FileUtils;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/json ")
    public String hello() {
        LocalDateTime now = LocalDateTime.now();
        String ipAddress = getIPAddress();
        String message = "hello frome the server";

        Map<String, String> response = new HashMap<>();
        response.put("ipAddress", ipAddress);
        response.put("date", now.toString());
        response.put("message", message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return "";

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    String ipAddress = getIPAddress();

    @GetMapping(value = "/api/image", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestBody String clientIP) throws IOException {
        // Retrieve the image based on clientIP and return it as response
        // Replace the logic below with your own implementation
        if (clientIP.equals(ipAddress)) {
            Path imagePath = Path.of("C:\\java project\\apiWeb\\src\\main\\java\\com\\example\\apiweb\\margot robbie.jpg"); // Replace with the actual image path
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}