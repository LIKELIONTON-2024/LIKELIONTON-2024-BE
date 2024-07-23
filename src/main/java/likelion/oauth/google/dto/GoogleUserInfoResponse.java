package likelion.oauth.google.dto;

public record GoogleUserInfoResponse(
	String email,
	String name,
	String token,
	boolean isJoined
) {
	public static GoogleUserInfoResponse from(String email, String name, String token, boolean isJoined) {
		return new GoogleUserInfoResponse(email, name, token, isJoined);
	}
}
