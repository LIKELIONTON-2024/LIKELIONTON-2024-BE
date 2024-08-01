package likelion.spot.dto;

public record SpotRecommendResponse(

        Long userId,
        Long spotId,
        String name,
        String latitude,
        String longitude
){}
