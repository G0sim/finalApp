package org.edupoll.controller;

import org.edupoll.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@RestController
//@RequestMapping("/resource")
@RequiredArgsConstructor
@Slf4j
public class ResourceController {

	private UserService userService;

	// 특정 경로로 왔을때 이미지를 보내주는 Controller
//	@GetMapping("/profile/{filename}")
	public ResponseEntity<?> getResourceHandle(HttpServletRequest request) {

//		log.info("url : {} " , request.getRequestURL().toString());
//		log.info("uri : {} ", request.getRequestURI().toString());
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//		headers.add("content-type", "image/png");
//		Resource resource = userService.loadResource(request.getRequestURL().toString());
		
		
		
//		ResponseEntity<Resource> response = new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
//				//파일을 전송할때는 resource사용 

//		return response;
		return null;
	}
}
