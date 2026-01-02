package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccountService accountService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void register_shouldCreateAccount_whenUsernameDoesNotExist() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("user1");
        dto.setPassword("1234");

        Account account = new Account();
        account.setId(1L);
        account.setUsername("user1");
        account.setPassword(encoder.encode("1234"));

        Mockito
                .when(accountRepository.findAccountByUsername("user1"))
                .thenReturn(Optional.empty());
        Mockito
                .when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(account);

        String result = accountService.register(dto);

        Assertions.assertEquals("Account created successfully!", result);
    }

    @Test
    void register_shouldReturnError_whenUsernameAlreadyExists() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("user1");
        dto.setPassword("1234");

        // Mock findAccountByUsername to return existing user
        Account existing = new Account();
        existing.setId(1L);
        existing.setUsername("user1");
        Mockito
                .when(accountRepository.findAccountByUsername("user1"))
                .thenReturn(Optional.of(existing));

        String result = accountService.register(dto);

        Assertions.assertEquals("Username is already exists", result);
    }

    @Test
    void login_shouldReturnTokens_whenCredentialsAreValid() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("username");
        dto.setPassword("password");

        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword(encoder.encode("password"));

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));
        Mockito
                .when(jwtUtil.generateAccessToken(Mockito.anyString()))
                .thenReturn("ACCESS_TOKEN");
        Mockito
                .when(jwtUtil.generateRefreshToken(Mockito.anyString()))
                .thenReturn("REFRESH_TOKEN");

        AuthResponseDTO authResponseDTO = accountService.login(dto);

        Assertions.assertEquals("ACCESS_TOKEN",authResponseDTO.getAccessToken());
        Assertions.assertEquals("REFRESH_TOKEN",authResponseDTO.getRefreshToken());
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("wrongUsername");

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> accountService.login(dto)
        );
        Assertions.assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid(){
        AccountDTO dto = new AccountDTO();
        dto.setUsername("user1");
        dto.setPassword("wrongPassword");

        Account account = new Account();
        account.setId(1L);
        account.setUsername("user1");
        account.setPassword(encoder.encode("correctPassword"));

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> accountService.login(dto));

        Assertions.assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists(){
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword("encodedPassword");

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));

        UserDetails userDetails = accountService.loadUserByUsername("username");

        Assertions.assertEquals("username", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
        Assertions.assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist(){
        Mockito
                .when(accountRepository.findAccountByUsername("invalidUserName"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException ex = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> accountService.loadUserByUsername("invalidUserName"));

        Assertions.assertEquals("User not found", ex.getMessage());
    }

    //TODO: update and delete account
}
