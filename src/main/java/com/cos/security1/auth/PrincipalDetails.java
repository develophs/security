package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 생성해준다.
// Security ContextHolder에 세션정보를 저장한다.
// 세션에 들어갈 수 있는 오브젝트가 정해져있다. =>Authentication 
// Authentication안에 User정보가 있어야됨.
// User오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails 타입(PrincipalDetails로 구현)으로 고정되어있다.
@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private final User user; //콤포지션

	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 해당 User의 권한을 리턴하는곳.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면계정.
		// 현재시간 - 로그인시간 =>1년을 초과하면 return false;
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
