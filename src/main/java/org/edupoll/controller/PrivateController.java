package org.edupoll.controller;

import org.edupoll.model.dto.UserWrapper;
import org.edupoll.service.JWTService;
import org.edupoll.service.KakaoAPIService;
import org.edupoll.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/user/private")
public class PrivateController {
	
	private final UserService userService;
	private final JWTService jwtService;
	private final KakaoAPIService kakapAPIService;
	
//	@GetMapping
//	public ResponseEntity<?> getLogonUserHandle(Authentication authentication) throws NotExistUserException{
//		log.info("authentication : {} , {}", authentication.getPrincipal());
//		
//		String principal = (String)authentication.getPrincipal();
//		UserWrapper 
//	}
	
	
	
	//사용자 상태 업데이트(프로필 이미지/이름)
	//사진을 업로드 해야해서 post
	//파일 업로드는 컨텐츠 타입이 multipart/form-data로 들어옴
	//file유형과 text유형이 혼재되어있음
//	@PostMapping("/info")
//	public ResponseEntity<?> updatedProfileHandle(@RequestHeader String token,
//			UpdateProfileRequest request) throws IOException, NotSupportedException {
//		String emailValue = jwtService.verifyToken(token);
//		
//		userService.modifySpecificUser(emailValue, request);
//		var wrapper = userService.searchUserByEmail(emailValue);
//		var response = new LogonUserInfoResponse(200, wrapper);
		
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
	
//	@GetMapping
//	public ResponseEntity<?> getLogonUserHandle(@RequestHeader String token) {
//		
//		String tokenEmailValue = jwtService.verifyToken(token);
//		
//		UserWrapper wrapper = userService.searchUserByEmail(tokenEmailValue);
//		
//		return new ResponseEntity<>(wrapper, HttpStatus.OK);
//	}
}
