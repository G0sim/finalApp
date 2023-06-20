package org.edupoll.model.dto.request;

import org.edupoll.model.entity.User;

public class UserCreateRequestData {

	Long id;
	String email;
	String password;
	String name;
	String profileImage;
	String social;

	public User toEntity() {
		return new User(this.id, this.email, this.password, this.name, this.profileImage, this.social);
	}

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}

	public UserCreateRequestData() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

}
