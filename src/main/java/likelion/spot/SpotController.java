package likelion.spot;

import likelion.auth.JwtTokenUtil;
import likelion.spot.dto.SpotRecommendResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spot")
public class SpotController {

    private final SpotService spotService;
    private final JwtTokenUtil jwtTokenUtil;

    public SpotController(SpotService spotService,JwtTokenUtil jwtTokenUtil){
        this.spotService=spotService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @GetMapping("/recommend")
    public List<SpotRecommendResponse> getRecommendSpots(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "0.2") double radius){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long userId = Long.parseLong(userIdStr);

        return spotService.recommendSpots(userId,radius);
    }
}
