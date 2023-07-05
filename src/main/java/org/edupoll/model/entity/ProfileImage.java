package org.edupoll.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="profileImages")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	//실제 파일 위치
	private String fileAddress;
	//파일 경로 웹상 위치
	private String url;
	
}
