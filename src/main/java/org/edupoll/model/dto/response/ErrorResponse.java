package org.edupoll.model.dto.response;

public class ErrorResponse {

	String message;
	Long timestamp;

	public ErrorResponse(String message, Long timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

}
