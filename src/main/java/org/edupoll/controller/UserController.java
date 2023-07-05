package org.edupoll.controller;

import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.request.CertifyRequest;
import org.edupoll.model.dto.request.UserCreateRequestData;
import org.edupoll.model.dto.request.VerifyCodeRequest;
import org.edupoll.model.dto.request.VerifyEmailRequest;
import org.edupoll.model.dto.response.CertifyResponse;
import org.edupoll.model.dto.response.UserResponseData;
import org.edupoll.model.dto.response.VerifyEmailResponse;
import org.edupoll.service.JWTService;
import org.edupoll.service.UserService;
import org.edupoll.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	VerifyService verifyService;

	@Autowired
	JWTService jwtService;

	// 회원가입하는 controller method
	@PostMapping("/signup")
	public ResponseEntity<UserResponseData> userSignUpHandle(UserCreateRequestData rd,
			@PathVariable @Valid String email) throws ExistUserEmailException, VerifyCodeException {

		userService.createUser(rd, email);

		return new ResponseEntity<>(HttpStatus.OK);

	}
	
	//이메일 사용 가능 여부 확인해주는 API
	@GetMapping("/available")
	public ResponseEntity<Void> availableHandle(@Valid VerifyEmailRequest request) throws ExistUserEmailException{
		userService.emailAvailableCheck(request);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//이메일 인증코드를 발급해주는 API
	@PostMapping("/verify-email")
	public ResponseEntity<?> verifyemailHEntity(@Valid CertifyRequest req, CertifyResponse resp) throws Exception{
		
		verifyService.sendCetifyMail(resp.getEmail(), req, resp);
		var response 
		= new VerifyEmailResponse(200, "이메일 인증코드가 정상 발급되어있습니다");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//이메일 인증코드를 검증해주는 API
	@PatchMapping("/verify-email")
	public ResponseEntity<Void> verifyCodeHandle(@Valid VerifyCodeRequest req) throws Exception {
		
		verifyService.verifySpecificCode(req);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

//	@PostMapping("/login")
//	public ResponseEntity<UserResponseData> userLogInHandle(UserLogInRequestData dto, @PathVariable @Valid String email, @PathVariable String password){
//		String data = userService.logInUser(dto, email, password);
//		if(data =="error1") {
//			return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
//		}
//		if(data=="error2") {
//			return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
//		}
//		return new ResponseEntity<UserResponseData>(HttpStatus.OK);
//	}
//	
//	@PostMapping("/certify")
//	public void certifyHandle(@PathVariable String email, CertifyRequest req, CertifyResponse resp)
//		throws ExceptionHandleConfig, MessagingException{
//		
//		mailService.sendCerifyMail(email, req, resp);
//	}
//	
//	@PostMapping("/check")
//	public ResponseEntity<Void> matchesCodeNEmailHandle(String code, CertifyResponse resp){
//		mailService.matchesEmailNCode(code, resp);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//		
//	}
//	
//	@PatchMapping("/private/password")
//	public ResponseEntity<Void> updatePassword(UserResponseData dto, String password){
//		
//		//인증토큰 여부 확인하기
////		if() {
////			
////		}
//		if(userService.checkPassword(dto, password)!=null) {
//			//비밀번호가 확인이 되었음. 변경하는 명령어작성 
//			String userEmail = userService.checkPassword(dto, password);
//			
//			
//			
//			
//			return new ResponseEntity<>(HttpStatus.OK);
//		}else {
//			//비밀번호가 맞지 않음
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//		}
//	}

//	@PostMapping("/validate")
//	public ResponseEntity<Void> validateHandle(@Valid ValidateUserRequest req)
//		throws NotExistUserException, InvalidPasswordException {
//		
//		userService.validateUser(req);
//
//		String token = jwtService.createToken(req.getEmail());
//		
//		log.info("token = " +token);
//	
//	
//	
//		=========================================================
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
