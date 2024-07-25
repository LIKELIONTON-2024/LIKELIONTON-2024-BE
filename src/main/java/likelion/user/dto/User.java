package likelion.user.dto;

import java.time.LocalDateTime;

public record User(

	String email,
	String nickname,
	String userImage,
	String userAddress,//주소
	Float latitude,//위도
	Float longitude,//경도
	Integer zipCode,//우편번호
	LocalDateTime createdDate

) {}

