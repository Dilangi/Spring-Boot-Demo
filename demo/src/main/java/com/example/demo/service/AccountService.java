package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(@RequestBody AccountDTO dto){
        if(repo.findAccountByUsername(dto.getUsername()).isPresent()){
            return "Username is already exists";
        }

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(encoder.encode(dto.getPassword()));

        repo.save(account);
        return "Account created successfully!";
    }

    public String login(@RequestBody AccountDTO dto){
        Account account = repo.findAccountByUsername(dto.getUsername())
                .orElse(null);

        if(account != null && encoder.matches(dto.getPassword(), account.getPassword())){
            return jwtUtil.generateToken(account.getUsername());
        }

        return "Invalid credentials";
    }
}
