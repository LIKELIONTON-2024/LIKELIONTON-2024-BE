package likelion.friend.dto;

public record FriendNicknameSearchResponse (
        Long userId,
        String nickname,
        String userImage,
        String status
){}
