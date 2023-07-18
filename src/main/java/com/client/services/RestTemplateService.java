package com.client.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Service
@Component
public class RestTemplateService {
    private final RestTemplate restTemplate;
    private final String SECRET_KEY = "79d50dc70da6c18e58d4f6ea8749ff69f01cb9e7ce062b43a191d62e9b587aa0";   // Encoded Key

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getServerData() throws HttpClientErrorException {

        String authToken = generateToken();                     // Generating new token
        System.out.println("Generated Token : " + authToken);
        String url = "http://localhost:8081/server/records";

        // Adding the authToken to headers of the request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization","Bearer " + authToken);

        try {
            // Making Request
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>("parameters", headers),
                    String.class
            );
            return response.getBody();
        }
        catch (Exception e) {
            // Handle other exceptions if needed
            System.out.println("Access Forbidden: " + e.getMessage());
            return "Sorry Either yor token is invalid or Expired \n"
                    + "Error: " + e.getMessage();
        }
    }

    //Method to generate a JWT token from the secret key
    public String generateToken(){
        String token =  Jwts
                .builder()
                .setSubject("client1")      // TODO- Hard coding client id, later fetch from a file
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *15)) // 15 seconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    // Method to return decoded Signing Key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
