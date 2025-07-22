package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
}
