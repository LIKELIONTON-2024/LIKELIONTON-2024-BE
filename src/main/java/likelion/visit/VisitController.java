package likelion.visit;

import likelion.auth.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visit")
public class VisitController {

    private final VisitService visitService;
    private final JwtTokenUtil jwtTokenUtil;

    public VisitController(VisitService visitService,JwtTokenUtil jwtTokenUtil){
        this.visitService=visitService;
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<String > createVisit(@RequestHeader("Authorization") String token, @RequestParam Long spotId){
        String jwtToken = token.substring(7);

        String userIdStr = jwtTokenUtil.getUserIdFromToken(jwtToken);
        Long userId = Long.parseLong(userIdStr);

        visitService.createVisit(userId, spotId);
        return ResponseEntity.ok("Verification complete");
    }
}
