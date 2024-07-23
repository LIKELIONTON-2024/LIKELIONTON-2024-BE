package likelion.oauth.google.dto;

import likelion.user.User;

public record GoogleUserInfoResponse(
	String email,
	String name,
	String token,
	User user,
	boolean isJoined
) {
	public static GoogleUserInfoResponse from(String email, String name, String token, User user, boolean isJoined) {
		return new GoogleUserInfoResponse(email, name, token, user, isJoined);
	}
}
