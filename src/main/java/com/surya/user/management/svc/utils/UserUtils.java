package com.surya.user.management.svc.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;

public class UserUtils {

    public static Optional<String> getRole(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority);
    }

}
