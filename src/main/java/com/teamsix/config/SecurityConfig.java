package com.teamsix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	//public可寫可不寫 密碼加密
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@SuppressWarnings("deprecation")
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable(); // 禁用CSRF保護
				
		http
			.authorizeRequests()
				.requestMatchers("/googlelogin").authenticated() // 需要授權的URL
				.and()
			.oauth2Login(); // 啟用OAuth 2.0登入
		
		return http.build();
	}
}
