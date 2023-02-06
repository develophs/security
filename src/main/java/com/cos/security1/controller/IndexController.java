package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View를 리턴하겠다!!
public class IndexController {

	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates(prefix), .mustache(suffix)
		// 스프링에서 지원해주기 때문에 따로 설정하지 않아도 된다. 생략가능!!!
		return "index"; //src/main/resources/templates/index.mustache
	}
	
}
