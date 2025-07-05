package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(AccountDTO dto){
        if(repo.findAccountByUsername(dto.getUsername()).isPresent()){
            return "Username is already exists";
        }

        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setPassword(encoder.encode(dto.getPassword()));

        repo.save(account);
        return "Account created successfully!";
    }

    public boolean login(AccountDTO dto){
        return repo.findAccountByUsername(dto.getUsername())
                .map(account -> encoder.matches(dto.getPassword(), account.getPassword()))
                .orElse(false);
    }
}
