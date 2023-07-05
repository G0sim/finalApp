package org.edupoll.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="feeds")
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@ManyToOne
//	@JoinColumn(name="writerId", referencedColumnName = "email")
	@JoinColumn(name="writerId")
	private User writer; 
	
	private String description;
	private Long viewCount;
	
	@OneToMany(mappedBy="feed")
	private List<FeedAttach> attaches;
}
