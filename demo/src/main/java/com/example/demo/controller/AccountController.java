package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String register(@RequestBody AccountDTO dto){
        return service.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountDTO dto){
        boolean success = service.login(dto);
        return success? "Login Succssful" : "Invalid Credentials";
    }
}
