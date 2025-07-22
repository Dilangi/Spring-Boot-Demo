package com.example.demo.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class AppConstants {
    //JWT
    public static class JWT{
        public static Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        public static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; //15 minutes
        public static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; //7 days
    }
    private AppConstants() {}
}
