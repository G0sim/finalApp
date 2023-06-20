package org.edupoll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.edupoll.model.dto.response.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import lombok.NoArgsConstructor;

@ControllerAdvice
@NoArgsConstructor
public class ExceptionHandleConfig extends Exception {
	
	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<ErrorResponse> badRequestHandle(BadRequest br){
		ErrorResponse err = new ErrorResponse("아이디나 비밀번호를 확인해주세요", System.currentTimeMillis());
		
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Unauthorized.class)
	public ResponseEntity<ErrorResponse> unauthorizedHandle(Unauthorized u){
		ErrorResponse err = new ErrorResponse("비밀번호가 일치하지 않습니다", System.currentTimeMillis());
		
		return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
	}
 }


