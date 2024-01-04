package com.dostavka24.dostavka24.service.email;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;


@Service
public final class EmailVerificationService {
    private String verifyCode;
    private Instant expiredDate;

    public String generateCode() {
        Random random = new Random();
        verifyCode = String.valueOf(random.nextInt(9999 - 1000 + 1) + 1000);
        expiredDate = Instant.now().plusSeconds(120);
        return String.valueOf(verifyCode);
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }
}
