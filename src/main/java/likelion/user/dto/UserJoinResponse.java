package likelion.user.dto;

public record UserJoinResponse(
	String accessToken,
	String refreshToken
) {
	public static UserJoinResponse from(String accessToken, String refreshToken) {
		return new UserJoinResponse(
			accessToken,
			refreshToken
		);
	}
}
