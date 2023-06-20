package org.edupoll.model.dto.request;

import java.text.SimpleDateFormat;

import org.edupoll.model.entity.ValidCode;

import lombok.Data;

@Data
public class CertifyRequest {

	Long id;
	String code;
	String email;
	String created;
	String state;

	

}
