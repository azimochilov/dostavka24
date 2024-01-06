package com.dostavka24.dostavka24.rest.users;

import com.dostavka24.dostavka24.service.secure.LogoutManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutManagerService logoutManagerService;

    @GetMapping
    public void logout() {
        logoutManagerService.logout();
    }
}
