package likelion.oauth.google.dto;

public record GoogleResponse(
	String access_token,
	String expires_in,
	String refresh_token,
	String scope,
	String token_type,
	String id_token
) {
}
