package org.edupoll.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class KakaoAuthorizedCallbackRequest {

	private String code;
	private String error;
}
