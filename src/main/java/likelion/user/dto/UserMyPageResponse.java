package likelion.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import likelion.user.User;

public record UserMyPageResponse(
	String nickname,
	String userImage,
	List<LocalDateTime> visitDays,
	Integer consecutiveDays,
	Float totalDistance,
	Integer totalSpots,
	Integer totalVisits,
	LocalDateTime startDay,
	Integer totalChuru
) {
	public static UserMyPageResponse from(User user, List<LocalDateTime> visitDays, int consecutiveDays) {
		return new UserMyPageResponse(
			user.getNickname(), user.getUserImage(), visitDays, consecutiveDays, user.getTotalDistance(),
			user.getTotalSpots(), user.getTotalVisits(), user.getCreatedDate(), user.getTotalChuru());
		);
	}
}
