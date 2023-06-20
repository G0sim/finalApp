package org.edupoll.model.dto.request;

import lombok.Data;

@Data//dto는 보통 data만 붙혀도 해결이 됨(lombok)
public class MailTestRequest {
	
	String eamil;
	String code;
	
}
