package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> login(@RequestBody AccountDTO dto){
        String tokenOrError = service.login(dto);
        return ResponseEntity.ok(tokenOrError);
    }
}
