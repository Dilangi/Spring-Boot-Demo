package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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

    public AuthResponseDTO login(AccountDTO dto){
        Account account = repo.findAccountByUsername(dto.getUsername())
                .orElse(null);

        if(account != null && encoder.matches(dto.getPassword(), account.getPassword())){
            String accessToken = jwtUtil.generateAccessToken(account.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(account.getUsername());
            return new AuthResponseDTO(accessToken, refreshToken);
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repo.findAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return  new User(
                account.getUsername(), account.getPassword(), new ArrayList<>()
        );
    }
}
