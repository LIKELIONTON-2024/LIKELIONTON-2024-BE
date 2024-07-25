package likelion.friendrequest.dto;

public record FriendRequestListResponse(
        Long userId,
        String nickname,
        String userImage
) {}
