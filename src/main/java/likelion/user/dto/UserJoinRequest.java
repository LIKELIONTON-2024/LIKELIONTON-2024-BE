package likelion.user.dto;

public record UserJoinRequest(
	String email,
	String nickname,
	Float latitude,
	Float longitude
) {
}
