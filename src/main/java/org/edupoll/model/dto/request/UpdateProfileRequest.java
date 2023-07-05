package org.edupoll.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/*
 * 기존 서블릿 처리랑은 다르게 Spring Boot에서는 Multipart 요청도 처리할 수 있게 기본 설정이 되어있음.
 * 
 * file type => MultipartFile / Text type =>상황에 맞는 자료형
 * 
 */
@Data
public class UpdateProfileRequest {

	private String name;
	private MultipartFile profile;
	//배열이나 리스트로 작성하면 같은 이름으로 넘어오는 객체들을 전부 저장해줌
	//private MultipartFile[] images;
	//private List<MultipartFile> images;
}
