package org.edupoll.service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.UserWrapper;
import org.edupoll.model.dto.request.UpdateProfileRequest;
import org.edupoll.model.dto.request.UserCreateRequestData;
import org.edupoll.model.dto.request.VerifyEmailRequest;
import org.edupoll.model.entity.ProfileImage;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.VerificationCode;
import org.edupoll.repository.ProfileImageRepository;
import org.edupoll.repository.UserRepository;
import org.edupoll.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.NotSupportedException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepository userRepository;
	// @RequiredArgsConstructor - private final : @Autowired 생략 가능 자동으로 생성

	@Autowired
	ProfileImageRepository profileImageRepository;

	@Autowired
	VerificationCodeRepository validCodeRepository;

	@Value("${jwt.secret.key}")
	String secretkey;

	@Value("${upload.server}")
	String uploadServer;

	@Value("${upload.basedir}")
	String baseDir;

	// 유저 회원가입 처리 할 service method
	public void createUser(UserCreateRequestData dto, String email)
			throws ExistUserEmailException, VerifyCodeException {
		// 같은 이메일로 가입되어 있었던 것인지 확인
		if (userRepository.findByEmail(email).isEmpty()) {
			throw new ExistUserEmailException("이미 등록된 이메일입니다.");
		}
		// 인증절차 거쳤는지 확인
		VerificationCode found = validCodeRepository.findByEmail(email);
		if (found.getState() == null) {
			throw new VerifyCodeException("이메일 인증을 해주세요.");
		}
		// 가입 여부와 인증 여부 확인하고 가입처리
		User one = new User();
		one.setEmail(email);
		one.setName(dto.getName());
		one.setPassword(dto.getPassword());
		userRepository.save(one);
	}

	// 특정 유저의 정보 업데이트
	// 스프링부트에서 파일을 업로드 하려면 서버에 잠깐 저장된 파일을 카피떠서 저장할것.
	@Transactional
	public void modifySpecificUser(String userEmail, UpdateProfileRequest request)
			throws IOException, NotSupportedException {
		log.info("req.name= {} ", request.getName());
		log.info("req.name= {} / {} ", request.getProfile().getContentType(),
				request.getProfile().getOriginalFilename());
		// 리퀘스트 객체에서 파일 정보를 뽑자
		MultipartFile multi = request.getProfile();
		// endsWith(png)||endsWith(jpg)

		// 해당 파일 컨텐츠 타입이 이미지인 경우에만 처리
		if (!multi.getContentType().startsWith("image")) {
			throw new NotSupportedException("이미지 파일만 설정 가능합니다.");
		}

		// 요청이 들어온 파일 옮기기 : 외부파일에 저장 후 연결하기
		// 기본 save경로는 properties에서 설정
		String emailEncoded = new String(Base64.getEncoder().encode(userEmail.getBytes()));

		File saveDir = new File(baseDir + "/profile/" + emailEncoded);
		saveDir.mkdir();

		// 파일명은 로그인 사용자의 email주소를 활용해서 저장
		String filename = System.currentTimeMillis()
				+ multi.getOriginalFilename().substring(multi.getOriginalFilename().lastIndexOf("."));

		File dest = new File(saveDir, filename);

		// 두개 조합해서 옮길 장소 설정
		// 옮겨서 업로드
		multi.transferTo(dest);

		/*
		 * File personalDir = new File(saveDir, filename); personalDir.mkdirs(); File
		 * dest = new Fiel(personalDir, "image"+ multi.getOriginalFilename()
		 * .substring(multi.getOriginalFilename().lastIndexOf("."));
		 */
		// 파일 정보를 DB에 insert
		var foundUser = userRepository.findByEmail(userEmail).get(); // 있는지 없는지 체크
		foundUser.setName(request.getName());
		foundUser.setProfileImage(uploadServer + "/resource/profile/" + emailEncoded + "/" + filename);
		userRepository.save(foundUser);

	}

	@Transactional
	public void emailAvailableCheck(@Valid VerifyEmailRequest request) throws ExistUserEmailException {
		boolean rst = userRepository.existsByEmail(request.getEmail());
		if (rst) {
			throw new ExistUserEmailException();
		}
	}

	// 파일명은 로그인 사용자의

	// 파일 정보를 DB에다가 insert
	// profileImage라는 entity를 생성해서 save
	// build라는 annotation을 적용해놨기 때문에 build라는 annotation을 활용할 수 있음
	// builder패턴은 마지막에 build()를 덧붙혀주어야함
//		ProfileImage one = ProfileImage.builder() //
//				.fileAddress(dest.getAbsolutePath()) //
//				.url(uploadServer + "/resource/profiles/" + filename) //
//				.build();
//		profileImageRepository.save(one);

//		var foundUser = userRepository.findByEmail(userEmail).get(); // 존재 여부 체크
//		foundUser.setName(request.getName());
//		foundUser.setProfileImage(one.getUrl());

//		userRepository.save(foundUser);

//	public UserWrapper searchUserByEmail(String tokenEmailValue) {
//		var found = userRepository.findByEmail(tokenEmailValue)//
//			.orElseThrow(()->new NotExistUserException);
//		
//		return new UserWrapper(found);
//	}

//	public Resource loadResource(String url) {
//		var found = 
//				profileImageRepository.findByUrl(url).orElseThrow(()->new NotExistUserExeption());
//		
//		new FileUrlResource(found.getFileAddress());
//		return null;
//	}

//	@Transactional
//	public void deleteSpecificSocialUser(String userEmail) {
//		var user = userRepository.findByEmail(userEmail).orElseThrow(()-> new NotExistUserException);
//		userRepository.delete(user);
//	}
//	
//	public String logInUser(UserLogInRequestData dto, String email, String password) {
//		if(userRepository.findByEmail(email).isEmpty()) {
//			return "error1";
//		}
//		if(dto.getPassword()!=password) {
//			return "error2";
//		}
//		
//		return "로그인완료";
//	}
//
//	public String checkPassword(UserResponseData dto, String password) {
//		
//		Algorithm algorithm = Algorithm.HMAC256(secretkey);
//		
//		var verifier = JWT.require(algorithm)
//							.build();
//		
//		String s =dto.getPassword();
//		
//		DecodedJWT decodedJWT = verifier.verify(s);
//		
//		if(decodedJWT.toString() != password) {
//			return null;
//		}else {
//			return "decodeJWT.getClaim(email).asString()";
//		}
//	}
//	
//	public void updatePassword(String password, String email) {
//		Algorithm algorithm = Algorithm.HMAC256(secretkey);
//		
//		User user=userRepository.findByEmail(email);
//		
//		user.setPassword(password);
//		
//	}
//
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
