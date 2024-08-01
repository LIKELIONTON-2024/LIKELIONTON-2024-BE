package likelion.oauth.google.dto;

public record GoogleRequest(
	String clientId,
	String clientSecret,
	String code,
	String redirectUri,
	String grantType
) {
}
