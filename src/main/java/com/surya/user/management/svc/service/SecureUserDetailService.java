package com.surya.user.management.svc.service;

import com.surya.user.management.svc.model.SecureUserDetails;
import com.surya.user.management.svc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecureUserDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(SecureUserDetailService.class);

    private final UserRepository userRepository;

    public SecureUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.surya.user.management.svc.model.UserDetails> userInfo = userRepository.findByEmailId(username);
        if(userInfo.isPresent()){
            logger.info("User Details: {}", userInfo.get());
            return new SecureUserDetails(userInfo.get());
        }else {
            logger.error("User name not found");
            throw new UsernameNotFoundException("User Not Found, Please Register!!");
        }
    }

}
