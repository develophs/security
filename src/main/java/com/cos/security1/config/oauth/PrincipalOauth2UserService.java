package com.cos.security1.config.oauth;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.config.oauth.provider.FacebookUserInfo;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.NaverUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder
			, UserRepository userRepository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}

	// 구글로 부터 받은 userRequest데이터에 대한 후처리를 하기위한 메서드
	// 함수종료시 @AuthenticationPrincipal 어노테이션이 생성된다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
		// registrationId로 어떤 OAuth로 로그인했는지 알 수 있다.
		
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
		
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글로그인 버튼 클릭 -> 구글 로그인 창 -> code를 리턴(OAuth-client라이브러리) ->AccessToken요청
		// 구글로그인을 하면 AccessToken을 OAuthClient 라이브러리가 자동으로 요청한다.
		// userRequest정보 -> loadUser함수호출 -> 구글로부터 회원프로필을 받아준다.
		System.out.println("getAttributes : " + oauth2User.getAttributes());
		
		//super.loadUser(userRequest).getAttributes() 정보로 회원가입을 진행시킨다.
		//강제회원가입 진행
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		} else {
			System.out.println("우리는 구글과 페이스북, 네이버만 지원해요");
		}
		
		String provider = oAuth2UserInfo.getProvider();//google,facebook
		String providerId = oAuth2UserInfo.getProviderId(); //username과 password는 사용할 일이 없다.
		String username = provider + "_" +providerId;
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) {
			System.out.println("OAuth 로그인이 처음입니다.");
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		} else {
			System.out.println("소셜 로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어있습니다.");
		}
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
	
}
