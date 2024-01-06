package com.dostavka24.dostavka24.domain.dtos.logins;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {
    private String accessToken;
}