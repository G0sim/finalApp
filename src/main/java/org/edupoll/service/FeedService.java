package org.edupoll.service;

import java.io.File;
import java.util.List;

import org.edupoll.model.dto.FeedWrapper;
import org.edupoll.model.dto.request.CreateFeedRequest;
import org.edupoll.model.entity.Feed;
import org.edupoll.model.entity.FeedAttach;
import org.edupoll.repository.FeedAttachRepository;
import org.edupoll.repository.FeedRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final UserRepository userRepository;
	private final FeedAttachRepository feedAttachRepository;

	@Value("${upload.basedir}")
	String uploadBaseDir;

	@Value("${upload.server}")
	String uploadServer;

	// 신규 글 등록을 처리할 서비스
	public boolean create(String principal, CreateFeedRequest request) throws Exception {
		// 1.feed entity생성해서 save
		var user = userRepository.findByEmail(principal).orElseThrow(() -> new Exception());
		var feed = new Feed();
		feed.setDescription(request.getDescription());
		feed.setViewCount(0L);
		feed.setWriter(user);
		var saved = feedRepository.save(feed);

		// 2.FeedAttach들을 생성 후 save
		log.info("attaches is exist? {}", request.getAttaches() != null);
		if (request.getAttaches() != null) {
			File uploadDirectory = new File(uploadBaseDir + "/feed/" + saved.getId());
			uploadDirectory.mkdirs();

			for (MultipartFile multi : request.getAttaches()) {
				// 파일들이 넘어왔다면 반복문을 돌려서 하나씩 transfer시킬것
				// 옮겨들 File 객체 및 위치 설정
				String fileName = String.valueOf(System.currentTimeMillis());
				String extension = multi.getOriginalFilename().split("\\.")[1];
				File dest = new File(uploadDirectory, fileName + "." + extension);

				// 옮기는 것을 진행
				multi.transferTo(dest);

				// 업로드가 끝났다면 DB에 기록할 것
				FeedAttach feedAttach = new FeedAttach();
				feedAttach.setType(multi.getContentType());
				// 업로드의 위치에 따라 결정되는 값
				feedAttach.setMediaUrl(
						uploadServer + "/resource/feed" + saved.getId() + "/" + fileName + "." + extension);
				feedAttach.setFeed(saved);
				feedAttachRepository.save(feedAttach);
			}
		}
		return true;
	}

	public Long size() {
		return feedRepository.count();
	}

	public List<FeedWrapper> allItems() {
		List<Feed> entityList= feedRepository.findAll(Sort.by("id").descending());
		return entityList.stream().map(e->new FeedWrapper(e)).toList();
	}
}
