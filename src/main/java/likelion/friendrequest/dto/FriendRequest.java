package likelion.friendrequest.dto;

import java.time.LocalDateTime;

public record FriendRequest(

        Long senderId,
        Long receiverId,
        String status,
        LocalDateTime createdDate

) {}
