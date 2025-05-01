package com.surya.user.management.svc.config;

import com.surya.user.management.svc.service.SecureUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final SecureUserDetailService secureUserDetailService;

    public SecurityConfig(SecureUserDetailService secureUserDetailService) {
        this.secureUserDetailService = secureUserDetailService;
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
		httpSec
		.authorizeHttpRequests(request -> request.requestMatchers("/customer/register",
				"/customer/login").permitAll().anyRequest().authenticated())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf.disable());
		
		return httpSec.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(secureUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
