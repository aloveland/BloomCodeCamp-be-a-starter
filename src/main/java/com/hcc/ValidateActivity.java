package com.hcc;

import com.hcc.requests.JwtTokenRequest;
import com.hcc.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate/validate")
public class ValidateActivity {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping()
    public ResponseEntity<String> validateToken(@RequestBody JwtTokenRequest tokenRequest) {
        try {

            boolean Valid = jwtUtil.validateToken(tokenRequest.getAccessToken());


            return ResponseEntity.ok("Access token is valid.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token is not valid.");
        }
    }
}

