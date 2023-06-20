package org.edupoll.service;

import java.util.Random;

import org.edupoll.model.dto.request.CertifyRequest;
import org.edupoll.model.dto.response.CertifyResponse;
import org.edupoll.model.entity.ValidCode;
import org.edupoll.repository.ValidCodeRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	private final ValidCodeRepository validCodeRepository;
	
	public void sendCerifyMail(String email, CertifyRequest req, CertifyResponse resp) 
		throws MessagingException {
		Random random = new Random();
		int RandNum = random.nextInt(1000000);
		String code = String.format("%05d", random);
		
		req.setCode(code);
		
		String Txt = """
				<div>
					<div>아래 인증번호를 정확하게 기입해주세요</div>
					<div style="border: 1px solid; padding: 5px;">#code</div>
				</div>
				
				""".replaceAll("#code", code);
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		//repository에 코드값 저장하기
		resp.setCode(code);
		resp.setEmail(req.getEmail());
		resp.setState("Y");
//		validCodeRepository.save(resp);
			//-->이 코드의 문제점 DB에 저장이 되지 않음. DB에 저장하는 처리를 해줄것
		//인증 메일을 발송 하고, 만약에 인증메일이 발송되어 있는 이메일이라면 
		//update처리해서 새로운 email을 보내게 할것
		
		helper.setTo(req.getEmail());
		helper.setFrom("edupoll@gmail.com");
		helper.setSubject("인증메일입니다.");
		helper.setText(Txt,true);
		
		mailSender.send(message);
	}
	
	public boolean matchesEmailNCode(String code, CertifyResponse resp) {
		ValidCode v= validCodeRepository.findByEmail(resp.getEmail());
		if(v.getCode().equals(resp.getCode())) {
			return true;
		}
		return false;
		
	}
	
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
