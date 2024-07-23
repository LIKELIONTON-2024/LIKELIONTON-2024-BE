package likelion.user.dto;

import java.time.LocalDateTime;

public record UserDTO(

        String email,
        String nickname,
        String userImage,
        Float latitude,//위도
        Float longitude,//경도
        LocalDateTime createdDate

){}
