package org.edupoll.controller;

import java.util.Base64;

import org.edupoll.exception.ExceptionHandleConfig;
import org.edupoll.model.dto.request.CertifyRequest;
import org.edupoll.model.dto.request.UserCreateRequestData;
import org.edupoll.model.dto.request.UserLogInRequestData;
import org.edupoll.model.dto.response.CertifyResponse;
import org.edupoll.model.dto.response.UserResponseData;
import org.edupoll.service.MailService;
import org.edupoll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	//회원가입하는 controller method
	@PostMapping("/signup")
	public ResponseEntity<UserResponseData> userSignUpHandle(UserCreateRequestData dto, @PathVariable @Valid String email){
		UserResponseData data = userService.createUser(dto, email);
		if(data!=null) {
			return new ResponseEntity<>(data, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseData> userLogInHandle(UserLogInRequestData dto, @PathVariable @Valid String email, @PathVariable String password){
		String data = userService.logInUser(dto, email, password);
		if(data =="error1") {
			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
		}
		if(data=="error2") {
			return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<UserResponseData>(HttpStatus.OK);
	}
	
	@PostMapping("/certify")
	public void certifyHandle(@PathVariable String email, CertifyRequest req, CertifyResponse resp)
		throws ExceptionHandleConfig, MessagingException{
		
		mailService.sendCerifyMail(email, req, resp);
	}
	
	@PostMapping("/check")
	public ResponseEntity<Void> matchesCodeNEmailHandle(String code, CertifyResponse resp){
		mailService.matchesEmailNCode(code, resp);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
//	@PostMapping("/validate")
//	public ResponseEntity<Void> validateHandle(@Valid ValidateUserRequest req)
//		throws NotExistUserException, InvalidPasswordException {
//		
//		userService.validateUser(req);
//		var resp = new ValidateUserResponse(200,  Base64.getEncoder().encodeToString(req.getEmail().getBytes());
//		String encoded = Base64.getEncoder().encodeToString(req.getEmail().getBytes());
//		log.info("encoded = " +encoded);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
//	@PostMapping("/mail-test")
//	public ResponseEntity<Void> mailTestHandle(MailTestRequest req){
//		mailService.sendTestSimpleMail(req.getEamil(req));
//		
//	}

}

