package likelion.oauth.google.dto;

public record GoogleUserInfoResponse(
	String email,
	String name,
	String picture
) {
}
