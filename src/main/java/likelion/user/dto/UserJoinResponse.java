package likelion.user.dto;

import likelion.user.User;

public record UserJoinResponse(
	Long id,
	String nickname,
	String userImage,
	Float latitude,
	Float longitude,
	String token
) {
	public static UserJoinResponse from(User user, String token) {
		return new UserJoinResponse(user.getUserId(), user.getNickname(), user.getUserImage(), user.getLatitude(),
			user.getLongitude(),
			token);
	}
}
