package com.example.demo.filter;

import com.example.demo.util.JwtUtil;
import jakarta.servlet.GenericFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtAuthFilter extends GenericFilter {
    @Autowired
    private JwtUtil jwtUtil;
}
