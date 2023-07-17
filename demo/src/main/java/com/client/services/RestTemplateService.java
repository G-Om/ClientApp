package com.client.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class RestTemplateService {
    private final RestTemplate restTemplate;
    private final String SECRET_KEY = "79d50dc70da6c18e58d4f6ea8749ff69f01cb9e7ce062b43a191d62e9b587aa0";

    @Autowired
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getServerData(){

        String authToken = generateToken();
        System.out.println(authToken);
        String url = "http://localhost:8081/server/records";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization","Bearer " + authToken);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                String.class
        );

        return response.getBody();
    }

    public String generateToken(){
        return generateToken(new HashMap<>());
    }

    public String generateToken(
            Map<String, Object> extraClaims) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject("client1")      // TODO- Hard coding client id, later fetch from a file
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *3600 *24 *365 *10)) // 10 years
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
