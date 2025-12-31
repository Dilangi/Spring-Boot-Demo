package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccountService accountService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Test
    void register_shouldSaveNewAccount() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("user1");
        dto.setPassword("pass123");

        Account account = new Account();
        account.setId(1L);
        account.setUsername(dto.getUsername());
        account.setPassword(encoder.encode(dto.getPassword()));

        Mockito.when(accountRepository.findAccountByUsername("user1")).thenReturn(Optional.empty());
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        Mockito.when(modelMapper.map(Mockito.any(Account.class), Mockito.eq(AccountDTO.class)))
                .thenAnswer(invocation -> {
                    Account arg = invocation.getArgument(0);
                    return new AccountDTO(arg.getId(), arg.getUsername(), null, 0);
                });

        String result = accountService.register(dto);

        Assertions.assertEquals("Account created successfully!", result);
    }

    @Test
    void register_existingUsername_shouldThrow() {
        AccountDTO dto = new AccountDTO();
        dto.setUsername("user1");
        dto.setPassword("pass123");

        // Mock findAccountByUsername to return existing user
        Account existing = new Account();
        existing.setId(1L);
        existing.setUsername("user1");
        Mockito.when(accountRepository.findAccountByUsername("user1")).thenReturn(Optional.of(existing));

        String result = accountService.register(dto);

        Assertions.assertEquals("Username is already exists", result);
    }
}
