package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

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

    public String login(AccountDTO dto){
        Account account = repo.findAccountByUsername(dto.getUsername())
                .orElse(null);

        if(account != null && encoder.matches(dto.getPassword(), account.getPassword())){
            return jwtUtil.generateAccessToken(account.getUsername());
        }

        return "Invalid credentials";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repo.findAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return  new org.springframework.security.core.userdetails.User(
                account.getUsername(), account.getPassword(), new ArrayList<>()
        );
    }
}
