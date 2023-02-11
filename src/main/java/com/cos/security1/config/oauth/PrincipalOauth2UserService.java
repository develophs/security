package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	// 구글로 부터 받은 userRequest데이터에 대한 후처리를 하기위한 메서드
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
		// registrationId로 어떤 OAuth로 로그인했는지 알 수 있다.
		
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
		// 구글로그인 버튼 클릭 -> 구글 로그인 창 -> code를 리턴(OAuth-client라이브러리) ->AccessToken요청
		// 구글로그인을 하면 AccessToken을 OAuthClient 라이브러리가 자동으로 요청한다.
		// userRequest정보 -> loadUser함수호출 -> 구글로부터 회원프로필을 받아준다.
		System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		//super.loadUser(userRequest).getAttributes() 정보로 회원가입을 진행시킨다.
		return super.loadUser(userRequest);
	}
	
}
