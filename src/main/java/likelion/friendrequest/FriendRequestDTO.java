package likelion.friendrequest;

import java.time.LocalDateTime;

public record FriendRequestDTO(

        Long senderId,
        Long receiverId,
        String status,
        LocalDateTime createdDate

) {}
