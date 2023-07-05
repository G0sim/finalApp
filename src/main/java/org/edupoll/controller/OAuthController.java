package org.edupoll.controller;

import org.edupoll.model.dto.KakaoAccessTokenWrapper;
import org.edupoll.model.dto.KakaoAccount;
import org.edupoll.model.dto.request.KakaoAuthorizedCallbackRequest;
import org.edupoll.model.dto.request.KakapValidateRequest;
import org.edupoll.model.dto.response.OAuthSignResponse;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.model.entity.User;
import org.edupoll.service.JWTService;
import org.edupoll.service.KakaoAPIService;
import org.edupoll.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {
	
//	private final KakaoAPIService kakaoApiService;
//	
//	@Value("${kakao.restapi.key}")
//	String kakaoRestApukey;
//	
//	@Value("${kakao.redirect.url}")
//	String kakaoRedirectUrl;
//	
////	private final KakaoAPIService;
////	private final JWTService;
////	private final UserService;
//
//	@GetMapping("/kakao")
//	public ResponseEntity<OAuthSignResponse> oauthKakaoHandle() {
//
//		var response = new OAuthSignResponse(200,
//				"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=9f8a6a7927eb6a0100879345011ab7ca&redirect_uri="+kakaoRedirectUrl);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//	
//	//이 부분이 카카오 로그인 후 코드를 받는 곳, 프론트에서 받아야 하는 부분인데 페이지가 없어서 일단 백에서 처리해둠
//	//나중에 프론트에서 처리하고 백으로 전달하는 방식으로 바뀔것.
//	@GetMapping("/kakao/callback")
//	public ResponseEntity<Void> oAuthKakaoCallbackHandle(KakaoAuthorizedCallbackRequest req) {
//		log.info("code = {} ", req.getCode());
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
//	@PostMapping("/kakao")
//	public ResponseEntity<ValidateUserResponse> oauthKakaoPontHandle(KakapValidateRequest req) 
//			throws JsonMappingException, JsonProcessingException{
//		
//		KakaoAccessTokenWrapper wrapper = kakaoApiService.getAccessToken(req.getCode());
//		KakaoAccount account = kakaoApiService.getUserInfo(wrapper.getAccessToken());
//		log.info("kakao = {}" , account.toString() );
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	//인증코드를 확보한 카카오 유저가 없다면 save
	//있다면 update - (accessToken)
//	public void updateKakaoUser(KakaoAccount account, String accessToken) {
//		Optional<User> user = userRepository.findByEmail(account.getEmail());
//		if(_user.isPresent()) {
//			User saved=_user.get();
//			saved.setSocial(accessToken);
//			userRepository.save(saved);
//		} else {
//			User user = new User();
//			user.setEmail(account.getEmail());
//			user.setName(account.getNickname());
//			user.setProfileImage(account.getProfileImage());
//			user.setSocial(accessToken);
//			userRepository.save(user);
//		}
//	}

}
