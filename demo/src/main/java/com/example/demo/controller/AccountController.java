package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/v1/auth")
public class AccountController {
    @Autowired
    private AccountService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountDTO dto){
        String result = service.register(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountDTO dto){
        try{
            AuthResponseDTO response = service.login(dto);
            return ResponseEntity.ok(response);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    //TODO refresh token
}
