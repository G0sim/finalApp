package org.edupoll.service;

import org.edupoll.model.dto.request.UserCreateRequestData;
import org.edupoll.model.dto.request.UserLogInRequestData;
import org.edupoll.model.dto.response.UserResponseData;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	//@RequiredArgsConstructor - private final : @Autowired 생략 가능 자동으로 생성
	
	//유저 회원가입 처리 service method
	public UserResponseData createUser(UserCreateRequestData dto, String email) {
		if(userRepository.findByEmail(email).isEmpty()) {
			User u = dto.toEntity();
			User saved=userRepository.save(u);
			return new UserResponseData(saved);			
		}
		return null;
		
//		User found = userRepository.findByEmail(dto.getEmail());
//		if(found != null) {
//			throw new ExistUserEmailException(); 
//		}
//		User one = new User();
//			one.setEmail(dto.getEmail());
//			one.setName(dto.getName());
//			one.setPassword(dto.getPassword());
//		userRepository.save(one);
	}
	
	public String logInUser(UserLogInRequestData dto, String email, String password) {
		if(userRepository.findByEmail(email).isEmpty()) {
			return "error1";
		}
		if(dto.getPassword()!=password) {
			return "error2";
		}
		
		return "로그인완료";
	}
	
//	public void validateUser(ValidateUserRequest req)
//		throws NotExistUserException, InvalidPasswordException {
//		User found = userRepository.findByEmail(req.getEmail());
//		if(found==null) {
//			throws new NotExsitUserExcepion();
//		}
//		
//		boolean isSame = found.getPassword().equals(req.getPassword());
//		if(!isSame) {
//			throw new InvalidPasswordException();
//		}
//	}
	
	

}
