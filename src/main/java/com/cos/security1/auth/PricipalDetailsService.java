package com.cos.security1.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 시큐리티 설정에서 loginProcessUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 
// IoC되어 있는 loadUserByUsername 메서스가 실행된다.

@Service
public class PricipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	public PricipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 시큐리티 session(내부 Authentication(내부 UserDetails))
	// 함수종료시 @AuthenticationPrincipal 어노테이션이 생성된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
	
}
