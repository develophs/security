package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;

//일반적으로 1.코드받기(인증) 2.코드+헤더+바디 > 엑세스토큰(권한)
//3. 권한을 통해 사용자 프로필 정보를 가져온다. 4-1.정보를 토대로 회원가입을 자동으로 진행시키기도 한다.
//4-2. 사용자정보를 추가적으로 받는경우 추가적으로 정보를 입력하여 수동으로 회원가입 진행


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled =true) // secured 어노테이션 활성화, preAuthorize, postAuthorize어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	//해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() //﻿인증만 되면 들어갈 수 있는 주소!!
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //인증 및 권한필요
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")
		// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다.
		// 컨트롤러에 /login을 맵핑안해도 된다.
			.defaultSuccessUrl("/")
		// 성공하면 기본으로 가는 url
			.and()
			.oauth2Login()
			.loginPage("/loginForm")
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
		// 구글 로그인이 완료된 뒤의 후처리가 필요하다.
		//Tip. 구글로그인이 성공하면 코드X (엑세스토큰+사용자 프로필 정보를 한번에 받는다.)
		
	}

	
	
}
