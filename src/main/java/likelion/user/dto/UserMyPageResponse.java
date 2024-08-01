package likelion.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import likelion.user.User;

public record UserMyPageResponse(
	String nickname,
	String userImage,
	String badgeImage,
	List<LocalDateTime> visitDays,
	Integer consecutiveDays,
	Float totalDistance,
	Integer totalVisits,
	LocalDateTime startDay
) {
	public static UserMyPageResponse from(User user, List<LocalDateTime> visitDays, int consecutiveDays) {
		return new UserMyPageResponse(
			user.getNickname(),
			user.getUserImage(),
			user.getUserBadge(),
			visitDays,
			consecutiveDays,
			user.getTotalDistance(),
			user.getTotalVisits(),
			user.getCreatedDate()
		);
	}
}
