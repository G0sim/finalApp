package org.edupoll.service;

import java.net.URI;

import org.edupoll.model.dto.KakaoAccessTokenWrapper;
import org.edupoll.model.dto.KakaoAccount;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoAPIService {

	@Value("${kakao.restapi.key}")
	String kakaoRestApiKey;
	@Value("${kakao.redirect.url")
	String kakaoRedirectUrl;

	@Autowired
	UserRepository userRepository;

	public KakaoAccessTokenWrapper getAccessToken(String code) {
		// 콜백으로 받은 인증코드를 이용해서 카카오에서 를 받아와야 한다.
		String tokenURL = "https://kauth.kakao.com/oauth/token";

		RestTemplate template = new RestTemplate();

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoRestApiKey);
		body.add("redirect_uri", kakaoRedirectUrl);
		body.add("code", code);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<KakaoAccessTokenWrapper> result = template.postForEntity(tokenURL, entity,
				KakaoAccessTokenWrapper.class);

		log.info("result.statuscode = {}", result.getStatusCode());
		log.info("result.body = {}", result.getBody());

		return result.getBody();
	}

	// acceessToken을 사용하여 실제 카카오 사용자의 정보를 불러와야한다
	public KakaoAccount getUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {
		String dest = "https://kapi.kakao.com/v2/user/me";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RestTemplate template = new RestTemplate();

		RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(dest));

		ResponseEntity<String> response = template.exchange(request, String.class);
		log.info("reponse.statuscode = {}", response.getStatusCode());
		log.info("reponse.body = {}", response.getBody());

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(response.getBody());
//		long id = node.get("id").asLong();

		String email = node.get("kakao_account").get("email").asText();
		String nickname = node.get("kakao_account").get("profile").get("nickname").asText();
		String profileImage = node.get("kakao_account").get("profile").get("profile_image_url").asText();

		return new KakaoAccount(email, nickname, profileImage);
	}

	// accessToken을 통해서 kakao에 unlink 호출
//	public void senUnlink(String tokenEmailValue) {
//		User found = userRepository.findByEmail(tokenEmailValue).orElseThrow(() -> new NotExistUserExecption());
//
//		var accessToken = found.getSocial();
//		String apiAddress = "https:// kapi.kakao.com/v1/user/unlink";
//
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//		headers.add("Autorization", "Bearer " + accessToken);
//
//		RestTemplate template = new RestTemplate();
//
//		RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.POST, URI.create(apiAddress));
//		// 요청 보낼때 객체를 무엇으로 설정할것인가 ?
//		ResponseEntity<String> response = template.exchange(request, String.class);
//		
//		log.info("unlink response = {} ", response.getBody());
//		
//	}

}

/*
 * spring framework에서 restAPI를 호출하는걸 도와주기 위해서 restTemplate : 동기 (blocking IO)
 * webclient : 비동기 (Non-blocking IO)
 * 
 */
