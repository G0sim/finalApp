package org.edupoll.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class KakapValidateRequest {

	private String code;
}
