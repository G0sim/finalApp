package org.edupoll.controller;

import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
//lombok
public class FollowController {

	@GetMapping("/{user}/following")
	public ResponseEntity<Void> getFollowingList(@PathVariable String user,
			@RequestHeader(name = "Authenticated-User", required = false) String secret) {
			//request header에 인증을 받았는지, 받지 않았는지 판별
		
		
		if(secret == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String secretUser = new String(Base64.getDecoder().decode(secret));
		
		// lombok에 들어있는 log
		log.info("user=" + user + " / secret = " + secret + " / secretUser =" + secretUser);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
