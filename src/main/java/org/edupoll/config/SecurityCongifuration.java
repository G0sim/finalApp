package org.edupoll.config;

import org.edupoll.config.support.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityCongifuration {

	private final JWTAuthenticationFilter jwtAuthemticationFilter;
	
	@Bean
	SecurityFilterChain finalAppSecurityChain(HttpSecurity http) throws Exception {

		http.csrf(t -> t.disable());
		http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(t -> t.requestMatchers("/api/v1/user/private/**").authenticated()//
				//포스트 메소드일때만 인증이 필요하게 설정
				.requestMatchers(HttpMethod.POST,"/api/v1/feed/storage").authenticated()//
				//anyrequest는 else와 비슷한 느낌
				.anyRequest().permitAll());
		
		http.anonymous(t->t.disable());
		http.logout(t-> t.disable());
		http.addFilterBefore(jwtAuthemticationFilter, AuthorizationFilter.class);
		http.cors(t->t.disable());
		
		return http.build();
	}
}
