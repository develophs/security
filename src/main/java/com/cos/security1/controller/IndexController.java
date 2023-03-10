package com.cos.security1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller // View를 리턴하겠다!!
public class IndexController {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public IndexController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping("/test/login")
	@ResponseBody
	public String testLogin(Authentication authentication
			,@AuthenticationPrincipal PrincipalDetails userDetails) { //DI(의존성 주입)
		System.out.println("/test/login ============");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUser());
		System.out.println("userDeatils : " + userDetails.getUser());
		return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	@ResponseBody
	public String testOauthLogin(Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) { //DI(의존성 주입)
		System.out.println("/test/oauth/login ============");
		OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
		System.out.println("authentication : " + oauth2User.getAttributes());
		System.out.println("oauth2User : " + oauth.getAttributes());
		return "OAuth세션 정보 저장";
	}
	
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates(prefix), .mustache(suffix)
		// 스프링에서 지원해주기 때문에 따로 설정하지 않아도 된다. 생략가능!!!
		return "index"; //src/main/resources/templates/index.mustache
	}
	
	//OAuth 로그인을 해도 PrincipalDetails
	// 일반 로그인을 해도 PrincipalDetails
	@GetMapping("/user")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : " + principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "manager";
	}
	
	// 스프링 시큐리티 해당주소를 낚아챈다. - SecurityConfig 파일 생성후 작동이 안한다.
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		user.setRole("ROLE_USER");
		user.setPassword(encPassword);
		
		userRepository.save(user); 
		// 회원가입은 잘됨. 비밀번호 노출됨. 
		// 시큐리티로 로그인을 할수가없다. 이유는 패스워드가 암호화가 안되었기 때문에
		// 인코딩을해야 보안 + 로그인이 가능하다.
		return "redirect:/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	@ResponseBody
	public String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	@ResponseBody
	public String data() {
		return "데이터";
	}
	
	
	
}
