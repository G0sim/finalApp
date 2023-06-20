package org.edupoll.model.dto.response;

import java.text.SimpleDateFormat;

import org.edupoll.model.entity.ValidCode;

import lombok.Data;

@Data
public class CertifyResponse {

	Long id;
	String code;
	String email;
	String created;
	String state;
	
	public CertifyResponse(ValidCode v) {
		super();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		this.created = fm.format(v.getCreated());

		this.id = v.getId();
		this.code = v.getCode();
		this.email = v.getEmail();
		this.state = v.getState();
	}
}
