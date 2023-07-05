package org.edupoll.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateUserResponse {
	//인증 성공시에 보내주는 응답객체
	private int code;
	private String secret;
	private String userEmail;

}
