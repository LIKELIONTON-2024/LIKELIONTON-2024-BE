package likelion.oauth.google.dto;

public record GoogleUserInfoResponse(
	String email,
	String name,
	String accessToken,
	String refreshToken,
	boolean isJoined
) {
	public static GoogleUserInfoResponse from(String email, String name, String accessToken, String refreshToken,
		boolean isJoined) {
		return new GoogleUserInfoResponse(email, name, accessToken, refreshToken, isJoined);
	}
}
