package likelion.user.dto;

import java.time.LocalDateTime;

public record UserResponse(

        Long userId,
        String nickname,
        String userImage,
        LocalDateTime lastVerifiedDate

){}
