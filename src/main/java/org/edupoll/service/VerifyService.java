package org.edupoll.service;

import java.util.Date;
import java.util.Optional;

import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.request.CertifyRequest;
import org.edupoll.model.dto.request.VerifyCodeRequest;
import org.edupoll.model.dto.response.CertifyResponse;
import org.edupoll.model.entity.VerificationCode;
import org.edupoll.repository.VerificationCodeRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyService {

	private final JavaMailSender mailSender;
	private final VerificationCodeRepository verificationCodeRepository;

	//인증메일 발송처리하는 service method
	@Transactional
	public void sendCetifyMail(String email, CertifyRequest req, CertifyResponse resp) {
		// 인증을 했었는지 확인할 것.
		VerificationCode found = verificationCodeRepository.findByEmail(resp.getEmail());
		int secretNum = (int) (Math.random() * 1_000_000);
		String code = String.format("%06d", secretNum);

		if (found.getState() == null) {
			// 인증을 안했다면 새로 인증 메일 보내기
			SimpleMailMessage message = new SimpleMailMessage();
			String txt= """
						<div style="background-color: blue;">
							<div>본인 인증 절차에 따라 인증코드를 보내드립니다.</div>
							<div>아래 인증번호를 정확하게 기입해주세요</div>
							<div sytle="font-weight:bold;">인증코드 : #{code} </div>
						</div>
					""".replaceAll("#{code}", code) ;
			
			message.setTo(req.getEmail());
			message.setFrom("no-reply@gamil.com");
			message.setSubject("인증코드를 보내드립니다.");
			message.setText("""
							본인 인증 절차에 따라 인증코드를 보내드립니다.
							계정을 확인해주셔서 감사합니다.
							인증코드 : #{code}
					""".replace("#{code}",code));
			
			//repository에 코드값 저장하기
			var one = new VerificationCode();
				one.setCode(code);
				one.setEmail(req.getEmail());
				one.setCreated(new Date());
				
				verificationCodeRepository.save(one);
		}else {
			// 인증 메일은 보냈지만, 코드 인증을 안했다면 인증 메일 다시 보내기
			SimpleMailMessage message = new SimpleMailMessage();
			String txt= """
						<div style="background-color: blue;">
							<div>본인 인증 절차에 따라 인증코드를 보내드립니다.</div>
							<div>아래 인증번호를 정확하게 기입해주세요</div>
							<div sytle="font-weight:bold;">인증코드 : #{code} </div>
						</div>
					""".replaceAll("#{code}", code) ;
			
			message.setTo(req.getEmail());
			message.setFrom("no-reply@gamil.com");
			message.setSubject("인증코드를 보내드립니다.");
			message.setText("""
							본인 인증 절차에 따라 인증코드를 보내드립니다.
							계정을 확인해주셔서 감사합니다.
							인증코드 : #{code}
					""".replace("#{code}",code));
			
			//repository에 코드값 업데이트하기
			VerificationCode VerificationCode = verificationCodeRepository.findByEmail(resp.getEmail());
			VerificationCode.setCode(code);
			
			verificationCodeRepository.save(VerificationCode);
		}
	}
	//인증메일 검증 처리하는 service method
	@Transactional
	public void verifySpecificCode(@Valid VerifyCodeRequest req) throws Exception {
		Optional<VerificationCode> result = verificationCodeRepository.findAllByEmail(req.getEmail());
		VerificationCode found = result.orElseThrow(() -> new Exception());
		
		long elapsed = System.currentTimeMillis()-found.getCreated().getTime();
		
		if(elapsed > 1000 * 60 * 3) {
			throw new VerifyCodeException("인증코드 유효시간이 만료되었습니다.");
		}
		if(!found.getCode().equals(req.getCode())) {
			throw new VerifyCodeException("인증코드가 일치하지 않습니다.");
		}
		found.setState("passed");
		verificationCodeRepository.save(found);
		
	}
	

//	public void sendCerifyMail(String email, CertifyRequest req, CertifyResponse resp) 
//		throws MessagingException {
//		Random random = new Random();
//		int RandNum = random.nextInt(1000000);
//		String code = String.format("%05d", random);
//		
//		req.setCode(code);
//		
//		String Txt = """
//				<div>
//					<div>아래 인증번호를 정확하게 기입해주세요</div>
//					<div style="border: 1px solid; padding: 5px;">#code</div>
//				</div>
//				
//				""".replaceAll("#code", code);
//		
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		//repository에 코드값 저장하기
//		resp.setCode(code);
//		resp.setEmail(req.getEmail());
//		resp.setState("Y");
////		validCodeRepository.save(resp);
//			//-->이 코드의 문제점 DB에 저장이 되지 않음. DB에 저장하는 처리를 해줄것
//		//인증 메일을 발송 하고, 만약에 인증메일이 발송되어 있는 이메일이라면 
//		//update처리해서 새로운 email을 보내게 할것
//		
//		helper.setTo(req.getEmail());
//		helper.setFrom("edupoll@gmail.com");
//		helper.setSubject("인증메일입니다.");
//		helper.setText(Txt,true);
//		
//		mailSender.send(message);
//	}
//	
//	public boolean matchesEmailNCode(String code, CertifyResponse resp) {
//		ValidCode v= validCodeRepository.findByEmail(resp.getEmail());
//		if(v.getCode().equals(resp.getCode())) {
//			return true;
//		}
//		return false;
//		
//	}

//	public void sendTestSimpleMail(MailTestRequest dto) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		
//		message.setFrom("edupoll@gmail.com");
//		message.setTo(dto.getEamil());
//		message.setSubject("메일테스트");
//		message.setText("메일 테스트중입니다.\n불편을 드려 죄송합니다.");
//		
//		mailSender.send(message);
//	}
//	
//	public void sendHtmlMail(MailTestRequest dto)
//		throws MessagingException{
//		String uuid = UUID.randomUUID().toString();
//		Random random = new Random();
//		int randNum=random.nextInt(1000000);
//		String code=String.format("%06d",randNum);
//		
//		dto.setCode(code);
//		
//		String htmlTxt= """
//				<div>
//				<h1>메일테스트중</h1>
//				<p style="color : orange">
//					HTML 메세지도 <b>전송 가능</b>하다.
//				</p>
//				<p>
//					쿠폰번호 : <i>#uuid </i>
//				</p>
//			</div>
//			""".replaceAll("#uuid",uuid);
//		
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		helper.setTo(dto.getEamil());
////		helper.setFrom("edupoll@gmail.com");
//		helper.setSubject("메일테스트-2");
//		helper.setText(htmlTxt,true);
//		mailSender.send(message);
//		// """ = 쌍따옴표 세개짜리는 자바13에서 나온 텍스트블록
//	}
}
