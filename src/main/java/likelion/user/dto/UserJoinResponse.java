package likelion.user.dto;

import likelion.user.User;

public record UserJoinResponse(
	Long id,
	String nickname,
	Float latitude,
	Float longitude,
	String accessToken,
	String refreshToken
) {
	public static UserJoinResponse from(User user, String accessToken, String refreshToken) {
		return new UserJoinResponse(
			user.getUserId(),
			user.getNickname(),
			user.getLatitude(),
			user.getLongitude(),
			accessToken,
			refreshToken
		);
	}
}
