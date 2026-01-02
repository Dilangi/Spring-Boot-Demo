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

@ExtendWith(MockitoExtension.class) // Enables Mockito annotations
public class AccountServiceTest {
    // Mock the repository (no real DB calls)
    @Mock
    private AccountRepository accountRepository;

    // Mock JwtUtil (not require a real token generation in unit tests)
    @Mock
    private JwtUtil jwtUtil;

    // Inject mocks into AccountService
    @InjectMocks
    private AccountService accountService;

    // Use real encoder to test password matching behavior
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /* =========================
       REGISTER TEST CASES
       ========================= */
    @Test
    void register_shouldCreateAccount_whenUsernameDoesNotExist() {
        // GIVEN: input DTO
        AccountDTO dto = new AccountDTO();
        dto.setUsername("username");
        dto.setPassword("1234");

        // GIVEN: repository already contains this username
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword(encoder.encode("1234"));

        Mockito
                .when(accountRepository.findAccountByUsername("username"))
                .thenReturn(Optional.empty());
        Mockito
                .when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(account);

        // WHEN: register is called
        String result = accountService.register(dto);

        // THEN: success message returned
        Assertions.assertEquals("Account created successfully!", result);

        // AND: save() was called exactly once
        Mockito.verify(accountRepository, Mockito.times(1))
                .save(Mockito.any(Account.class));
    }

    @Test
    void register_shouldReturnError_whenUsernameAlreadyExists() {
        // GIVEN: input DTO
        AccountDTO dto = new AccountDTO();
        dto.setUsername("username");
        dto.setPassword("1234");

        // GIVEN: repository already contains this username
        Account existing = new Account();
        existing.setId(1L);
        existing.setUsername("username");

        Mockito
                .when(accountRepository.findAccountByUsername("username"))
                .thenReturn(Optional.of(existing));

        // WHEN: register is called
        String result = accountService.register(dto);

        // THEN: error message is returned
        Assertions.assertEquals("Username is already exists", result);

        // AND: save() must NOT be called
        Mockito.verify(accountRepository, Mockito.never())
                .save(Mockito.any(Account.class));
    }

    /* =========================
       LOGIN TEST CASES
       ========================= */

    @Test
    void login_shouldReturnTokens_whenCredentialsAreValid() {
        // GIVEN: login request
        AccountDTO dto = new AccountDTO();
        dto.setUsername("username");
        dto.setPassword("password");

        // GIVEN: account exists with encoded password
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword(encoder.encode("password"));

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));
        // GIVEN: JWT tokens are generated
        Mockito
                .when(jwtUtil.generateAccessToken(Mockito.anyString()))
                .thenReturn("ACCESS_TOKEN");
        Mockito
                .when(jwtUtil.generateRefreshToken(Mockito.anyString()))
                .thenReturn("REFRESH_TOKEN");

        // WHEN: login is called
        AuthResponseDTO authResponseDTO = accountService.login(dto);

        // THEN: tokens are returned
        Assertions.assertEquals("ACCESS_TOKEN",authResponseDTO.getAccessToken());
        Assertions.assertEquals("REFRESH_TOKEN",authResponseDTO.getRefreshToken());
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // GIVEN: login request
        AccountDTO dto = new AccountDTO();
        dto.setUsername("wrongUsername");

        // GIVEN: repository returns empty
        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // WHEN & THEN: exception is thrown
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> accountService.login(dto)
        );
        Assertions.assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid(){
        // GIVEN: login request with wrong password
        AccountDTO dto = new AccountDTO();
        dto.setUsername("username");
        dto.setPassword("wrongPassword");

        // GIVEN: account exists with different password
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword(encoder.encode("correctPassword"));

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
                () -> accountService.login(dto));

        // WHEN & THEN: exception is thrown
        Assertions.assertEquals("Invalid credentials", ex.getMessage());
    }

    /* =========================
       LOAD USER BY USERNAME
       ========================= */

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists(){
        // GIVEN: account exists
        Account account = new Account();
        account.setId(1L);
        account.setUsername("username");
        account.setPassword("encodedPassword");

        Mockito
                .when(accountRepository.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(account));

        // WHEN: Spring Security loads user
        UserDetails userDetails = accountService.loadUserByUsername("username");

        // THEN: correct user details returned
        Assertions.assertEquals("username", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
        Assertions.assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist(){
        // GIVEN: repository returns empty
        Mockito
                .when(accountRepository.findAccountByUsername("invalidUserName"))
                .thenReturn(Optional.empty());

        // WHEN & THEN: UsernameNotFoundException is thrown
        UsernameNotFoundException ex = Assertions.assertThrows(UsernameNotFoundException.class,
                () -> accountService.loadUserByUsername("invalidUserName"));

        Assertions.assertEquals("User not found", ex.getMessage());
    }

    //TODO: update and delete account
}
