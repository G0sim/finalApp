package org.edupoll.controller;

import java.util.List;

import org.edupoll.model.dto.FeedWrapper;
import org.edupoll.model.dto.request.CreateFeedRequest;
import org.edupoll.model.dto.response.FeedListResponse;
import org.edupoll.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

	private final FeedService feedService;

	@PostMapping("/storage")
	public ResponseEntity<?> createNewFeedHandle(@AuthenticationPrincipal String principal, CreateFeedRequest request)
			throws Exception {

		boolean r = feedService.create(principal, request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public ResponseEntity<?> readAllFeedHandle() {

		Long total = feedService.size();
		List<FeedWrapper> feeds = feedService.allItems();
		
		FeedListResponse response = new FeedListResponse(total, feeds);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
