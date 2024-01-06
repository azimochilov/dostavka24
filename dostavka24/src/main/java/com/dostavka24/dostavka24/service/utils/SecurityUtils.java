package com.dostavka24.dostavka24.service.utils;

import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUserId();
        }
        throw new NotFoundException("User not found");
    }

    public static String getCurrentUsername() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUsername();
        }
        throw new NotFoundException("User not found");
    }

}
