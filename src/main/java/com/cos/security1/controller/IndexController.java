package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴하겠다!!
public class IndexController {

	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates(prefix), .mustache(suffix)
		// 스프링에서 지원해주기 때문에 따로 설정하지 않아도 된다. 생략가능!!!
		return "index"; //src/main/resources/templates/index.mustache
	}
	
	@GetMapping("/user")
	@ResponseBody
	public String user() {
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
	@GetMapping("/login")
	@ResponseBody
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	@ResponseBody
	public String join() {
		return "join";
	}
	
	@GetMapping("/joinProc")
	@ResponseBody
	public String joinProc() {
		return "회원가입 완료됨";
	}
	
	
}
