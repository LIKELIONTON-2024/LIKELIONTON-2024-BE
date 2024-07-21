package likelion.chur;

import java.time.LocalDateTime;

public record ChurDTO(

        Long senderId,
        Long receiverId,
        LocalDateTime sendDate

) {}
