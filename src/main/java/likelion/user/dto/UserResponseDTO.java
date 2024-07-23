package likelion.user.dto;

import java.time.LocalDateTime;

public record UserResponseDTO (

        Long userId,
        String nickname,
        String userImage,
        LocalDateTime lastVerifiedDate

){}
